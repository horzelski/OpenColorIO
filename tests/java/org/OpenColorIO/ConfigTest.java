// SPDX-License-Identifier: BSD-3-Clause
// Copyright Contributors to the OpenColorIO Project.

import junit.framework.TestCase;
import org.OpenColorIO.*;
import java.nio.*;

public class ConfigTest extends TestCase {
    
    String SIMPLE_PROFILE = ""
    + "ocio_profile_version: 1\n"
    + "\n"
    + "search_path: luts\n"
    + "strictparsing: false\n"
    + "luma: [0.2126, 0.7152, 0.0722]\n"
    + "\n"
    + "roles:\n"
    + "  default: raw\n"
    + "  scene_linear: lnh\n"
    + "\n"
    + "displays:\n"
    + "  sRGB:\n"
    + "    - !<View> {name: Film1D, colorspace: vd8}\n"
    + "    - !<View> {name: Raw, colorspace: raw}\n"
    + "\n"
    + "active_displays: []\n"
    + "active_views: []\n"
    + "\n"
    + "colorspaces:\n"
    + "  - !<ColorSpace>\n"
    + "    name: raw\n"
    + "    family: raw\n"
    + "    bitdepth: 32f\n"
    + "    description: |\n"
    + "      A raw color space. Conversions to and from this space are no-ops.\n"
    + "      \n"
    + "    isdata: true\n"
    + "    allocation: uniform\n"
    + "\n"
    + "  - !<ColorSpace>\n"
    + "    name: lnh\n"
    + "    family: ln\n"
    + "    bitdepth: 16f\n"
    + "    description: |\n"
    + "      The show reference space. This is a sensor referred linear\n"
    + "      representation of the scene with primaries that correspond to\n"
    + "      scanned film. 0.18 in this space corresponds to a properly\n"
    + "      exposed 18% grey card.\n"
    + "      \n"
    + "    isdata: false\n"
    + "    allocation: lg2\n"
    + "\n"
    + "  - !<ColorSpace>\n"
    + "    name: vd8\n"
    + "    family: vd8\n"
    + "    bitdepth: 8ui\n"
    + "    description: |\n"
    + "      how many transforms can we use?\n"
    + "      \n"
    + "    isdata: false\n"
    + "    allocation: uniform\n"
    + "    to_reference: !<GroupTransform>\n"
    + "      children:\n"
    + "        - !<ExponentTransform> {value: [2.2, 2.2, 2.2, 1]}\n"
    + "        - !<MatrixTransform> {matrix: [1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1], offset: [0, 0, 0, 0]}\n"
    + "        - !<CDLTransform> {slope: [1, 1, 1], offset: [0.1, 0.3, 0.4], power: [1.1, 1.1, 1.1], saturation: 0.9}\n";
    
    String GLSLResult = ""
    + "\n"
    + "// Generated by OpenColorIO\n"
    + "\n"
    + "vec4 jnitestocio(in vec4 inPixel, \n"
    + "    const sampler3D lut3d) \n"
    + "{\n"
    + "vec4 out_pixel = inPixel; \n"
    + "out_pixel = out_pixel * mat4(1.08749, -0.0794667, -0.00802222, 0, -0.0236222, 1.03164, -0.00802222, 0, -0.0236222, -0.0794667, 1.10309, 0, 0, 0, 0, 1);\n"
    + "out_pixel = pow(max(out_pixel, vec4(0, 0, 0, 0)), vec4(0.909091, 0.909091, 0.909091, 1));\n"
    + "out_pixel = vec4(-0.1, -0.3, -0.4, -0) + out_pixel;\n"
    + "out_pixel = pow(max(out_pixel, vec4(0, 0, 0, 0)), vec4(0.454545, 0.454545, 0.454545, 1));\n"
    + "// OSX segfault work-around: Force a no-op sampling of the 3D LUT.\n"
    + "texture3D(lut3d, 0.96875 * out_pixel.rgb + 0.015625).rgb;\n"
    + "return out_pixel;\n"
    + "}\n"
    + "\n";
    
    protected void setUp() {
    }
    
    protected void tearDown() {
    }
    
    public void test_interface() {
        
        Config _cfg = new Config().CreateFromStream(SIMPLE_PROFILE);
        Config _cfge = _cfg.createEditableCopy();
        _cfge.clearEnvironmentVars();
        assertEquals(0, _cfge.getNumEnvironmentVars());
        _cfge.addEnvironmentVar("FOO", "test1");
        _cfge.addEnvironmentVar("FOO2", "test2${FOO}");
        assertEquals(2, _cfge.getNumEnvironmentVars());
        assertEquals("FOO", _cfge.getEnvironmentVarNameByIndex(0));
        assertEquals("FOO2", _cfge.getEnvironmentVarNameByIndex(1));
        assertEquals("test1", _cfge.getEnvironmentVarDefault("FOO"));
        assertEquals("test2${FOO}", _cfge.getEnvironmentVarDefault("FOO2"));
        assertEquals("test2test1", _cfge.getCurrentContext().resolveStringVar("${FOO2}"));
        //self.assertEqual({'FOO': 'test1', 'FOO2': 'test2${FOO}'}, _cfge.getEnvironmentVarDefaults())
        _cfge.clearEnvironmentVars();
        assertEquals(0, _cfge.getNumEnvironmentVars());
        assertEquals("luts", _cfge.getSearchPath());
        _cfge.setSearchPath("otherdir");
        assertEquals("otherdir", _cfge.getSearchPath());
        _cfge.sanityCheck();
        _cfge.setDescription("testdesc");
        assertEquals("testdesc", _cfge.getDescription());
        //assertEquals(SIMPLE_PROFILE, _cfg.serialize());
        //assertEquals("$07d1fb1509eeae1837825fd4242f8a69:$885ad1683add38a11f7bbe34e8bf9ac0",
        //             _cfg.getCacheID());
        Context con = _cfg.getCurrentContext();
        assertNotSame(0, con.getNumStringVars());
        //assertEquals("", _cfg.getCacheID(con)); // test env makes this fail
        //assertEquals("", _cfge.getWorkingDir());
        _cfge.setWorkingDir("/foobar");
        assertEquals("/foobar", _cfge.getWorkingDir());
        assertEquals(3, _cfge.getNumColorSpaces());
        assertEquals("lnh", _cfge.getColorSpaceNameByIndex(1));
        //public native ColorSpace getColorSpace(String name);
        assertEquals(0, _cfge.getIndexForColorSpace("foobar"));
        //public native void addColorSpace(ColorSpace cs);
        //public native void clearColorSpaces();
        //public native String parseColorSpaceFromString(String str);
        if(_cfg.isStrictParsingEnabled()) fail("strict parsing should be off");
        _cfge.setStrictParsingEnabled(true);
        if(!_cfge.isStrictParsingEnabled()) fail("strict parsing should be on");
        assertEquals(2, _cfge.getNumRoles());
        if(_cfg.hasRole("foo")) fail("shouldn't have role foo");
        _cfge.setRole("foo", "dfadfadf");
        assertEquals(3, _cfge.getNumRoles());
        if(!_cfge.hasRole("foo")) fail("should have role foo");
        assertEquals("foo", _cfge.getRoleName(1));
        assertEquals("sRGB", _cfge.getDefaultDisplay());
        assertEquals(1, _cfge.getNumDisplays());
        assertEquals("sRGB", _cfge.getDisplay(0));
        assertEquals("Film1D", _cfge.getDefaultView("sRGB"));
        assertEquals(2, _cfge.getNumViews("sRGB"));
        assertEquals("Raw", _cfge.getView("sRGB", 1));
        assertEquals("vd8", _cfge.getDisplayColorSpaceName("sRGB", "Film1D"));
        assertEquals("", _cfg.getDisplayLooks("sRGB", "Film1D"));
        
        // TODO: seems that 4 string params causes a memory error in the JNI layer?
        //_cfge.addDisplay("foo", "bar", "foo", 0);
        
        _cfge.clearDisplays();
        _cfge.setActiveDisplays("sRGB");
        assertEquals("sRGB", _cfge.getActiveDisplays());
        _cfge.setActiveViews("Film1D");
        assertEquals("Film1D", _cfge.getActiveViews());
        float luma[] = new float[3];
        _cfge.getDefaultLumaCoefs(luma);
        assertEquals(0.2126, luma[0], 1e-8);
        float[] newluma = new float[]{0.1f, 0.2f, 0.3f};
        _cfge.setDefaultLumaCoefs(newluma);
        float tnewluma[] = new float[3];
        _cfge.getDefaultLumaCoefs(tnewluma);
        assertEquals(0.1f, tnewluma[0], 1e-8);
        assertEquals(0, _cfge.getNumLooks());
        Look lk = new Look().Create();
        lk.setName("coollook");
        lk.setProcessSpace("somespace");
        ExponentTransform et = new ExponentTransform().Create();
        et.setValue(new float[]{0.1f, 0.2f, 0.3f, 0.4f});
        lk.setTransform(et);
        ExponentTransform iet = new ExponentTransform().Create();
        iet.setValue(new float[]{-0.1f, -0.2f, -0.3f, -0.4f});
        lk.setInverseTransform(iet);
        _cfge.addLook(lk);
        assertEquals(1, _cfge.getNumLooks());
        assertEquals("coollook", _cfge.getLookNameByIndex(0));
        Look glk = _cfge.getLook("coollook");
        assertEquals("somespace", glk.getProcessSpace());
        _cfge.clearLooks();
        assertEquals(0, _cfge.getNumLooks());
        
        //public native Processor getProcessor(Context context, ColorSpace srcColorSpace, ColorSpace dstColorSpace);
        
        Processor _proc = _cfg.getProcessor("lnh", "vd8");
        assertEquals(false, _proc.isNoOp());
        assertEquals(true, _proc.hasChannelCrosstalk());
        float packedpix[] = new float[]{0.48f, 0.18f, 0.9f, 1.0f,
                                        0.48f, 0.18f, 0.18f, 1.0f,
                                        0.48f, 0.18f, 0.18f, 1.0f,
                                        0.48f, 0.18f, 0.18f, 1.0f };
        FloatBuffer buf = ByteBuffer.allocateDirect(2 * 2 * 4 * Float.SIZE / 8).asFloatBuffer();
        buf.put(packedpix);
        PackedImageDesc foo = new PackedImageDesc(buf, 2, 2, 4);
        _proc.apply(foo);
        FloatBuffer wee = foo.getData();
        assertEquals(-2.4307251581696764E-35f, wee.get(2), 1e-8);
        float rgbfoo[] = new float[]{0.48f, 0.18f, 0.18f};
        _proc.applyRGB(rgbfoo);
        assertEquals(0.6875247f, rgbfoo[0], 1e-8);
        float rgbafoo[] = new float[]{0.48f, 0.18f, 0.18f, 1.f};
        _proc.applyRGBA(rgbafoo);
        assertEquals(1.f, rgbafoo[3], 1e-8);
        //assertEquals("$a92ef63abd9edf61ad5a7855da064648", _proc.getCpuCacheID());
        GpuShaderDesc desc = new GpuShaderDesc();
        desc.setLanguage(GpuLanguage.GPU_LANGUAGE_GLSL_1_3);
        desc.setFunctionName("jnitestocio");
        desc.setLut3DEdgeLen(32);
        String glsl = _proc.getGpuShaderText(desc);
        //assertEquals(GLSLResult, glsl);
        //assertEquals("$1dead2bf42974cd1769164e45a0c9e40", _proc.getGpuShaderTextCacheID(desc));
        int len = desc.getLut3DEdgeLen();
        int size = 3 * len * len * len;
        FloatBuffer lut3d = ByteBuffer.allocateDirect(size * Float.SIZE / 8).asFloatBuffer();
        _proc.getGpuLut3D(lut3d, desc);
        assertEquals(0.0f, lut3d.get(size-1));
        assertEquals("<NULL>", _proc.getGpuLut3DCacheID(desc));
        
        //public native Processor getProcessor(Context context, String srcName, String dstName);
        //public native Processor getProcessor(Transform transform);
        //public native Processor getProcessor(Transform transform, TransformDirection direction);
        //public native Processor getProcessor(Context context, Transform transform, TransformDirection direction);
        
        _cfge.dispose();
        _cfg.dispose();
        
        //System.out.println(_cfge.serialize());
        //_cfge.sanityCheck();
        //System.out.println(_cfge.getNumColorSpaces());
        //System.out.println(_cfg.getCacheID());
        //System.out.println(_cfge.serialize());
        
    }
    
}

# SPDX-License-Identifier: BSD-3-Clause
# Copyright Contributors to the OpenColorIO Project.

class RangeTransform:
    """
    RangeTransform
    """
    def __init__(self):
        pass

    def equals(self, range):
        """
        equals(range)
        
        :param range: a range transform
        :type range: :py:class:`PyOpenColorIO.RangeTransform`

        Returns True if range is equal to :py:class:`PyOpenColorIO.RangeTransform`.
        """
        pass

    def validate(self):
        """
        validate()
        
        Throw if :py:class:`PyOpenColorIO.RangeTransform` contains illegal parameters.
        """
        pass

    def getStyle(self):
        """
        getStyle()
        
        Returns whether the range is Clamp or noClamp style.

        :return: the style name
        :rtype: string
        """
        pass

    def setStyle(self, value):
        """
        setStyle(value)
        
        Sets the range to Clamp or noClamp style.
        
        :param value: the style name
        :type value: string
        """
        pass

    def getMinInValue(self):
        """
        getMinInValue()
        
        Returns the minimum input value of :py:class:`PyOpenColorIO.RangeTransform`.

        :return: the minimum input value
        :rtype: float
        """
        pass

    def setMinInValue(self, value):
        """
        setMinInValue(value)
        
        Sets the minimum input value in :py:class:`PyOpenColorIO.RangeTransform`.
        
        :param value: minimum input value of range transform
        :type value: float
        """
        pass

    def hasMinInValue(self):
        """
        hasMinInValue()
        
        Returns true if the minimum input value of :py:class:`PyOpenColorIO.RangeTransform` is set.
        """
        pass

    def unsetMinInValue(self):
        """
        unsetMinInValue()
        
        Unsets the minimum input value in :py:class:`PyOpenColorIO.RangeTransform`.
        """
        pass

    def getMaxInValue(self):
        """
        getMaxInValue()
        
        Returns the maximum input value of :py:class:`PyOpenColorIO.RangeTransform`.

        :return: the maximum input value
        :rtype: float
        """
        pass
    
    def setMaxInValue(self, value):
        """
        setMaxInValue(value)
        
        Sets the maximum input value in :py:class:`PyOpenColorIO.RangeTransform`.
        
        :param value: maximum input value of range transform
        :type value: float
        """
        pass

    def hasMaxInValue(self):
        """
        hasMaxInValue()
        
        Returns true if the maximum input value of :py:class:`PyOpenColorIO.RangeTransform` is set.
        """
        pass

    def unsetMaxInValue(self):
        """
        unsetMaxInValue()
        
        Unsets the maximum input value in :py:class:`PyOpenColorIO.RangeTransform`.
        """
        pass

    def getMinOutValue(self):
        """
        getMinOutValue()
        
        Returns the minimum output value of :py:class:`PyOpenColorIO.RangeTransform`.

        :return: the minimum output value
        :rtype: float
        """
        pass
    
    def setMinOutValue(self, value):
        """
        setMinOutValue(value)
        
        Sets the minimum output value in :py:class:`PyOpenColorIO.RangeTransform`.
        
        :param value: minimum output value of range transform
        :type value: float
        """
        pass

    def hasMinOutValue(self):
        """
        hasMinOutValue()
        
        Returns true if the minimum output value of :py:class:`PyOpenColorIO.RangeTransform` is set.
        """
        pass

    def unsetMinOutValue(self):
        """
        unsetMinOutValue()
        
        Unsets the minimum output value in :py:class:`PyOpenColorIO.RangeTransform`.
        """
        pass

    def getMaxOutValue(self):
        """
        getMaxOutValue()
        
        Returns the maximum output value of :py:class:`PyOpenColorIO.RangeTransform`.

        :return: the maximum output value
        :rtype: float
        """
        pass
    
    def setMaxOutValue(self, value):
        """
        setMaxOutValue(value)
        
        Sets the maximum output value in :py:class:`PyOpenColorIO.RangeTransform`.
        
        :param value: maximum output value of range transform
        :type value: float
        """
        pass

    def hasMaxOutValue(self):
        """
        hasMaxOutValue()
        
        Returns true if the maximum output value of :py:class:`PyOpenColorIO.RangeTransform` is set.
        """
        pass

    def unsetMaxOutValue(self):
        """
        unsetMaxOutValue()
        
        Unsets the maximum output value in :py:class:`PyOpenColorIO.RangeTransform`.
        """
        pass


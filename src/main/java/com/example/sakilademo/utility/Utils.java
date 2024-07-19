package com.example.sakilademo.utility;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.crypto.spec.PSource;
import java.beans.PropertyDescriptor;
import java.util.*;

public class Utils {
    public static void copyNonNullProperties(Object src, Object target) {
        if (src == null || target == null) {
            throw new IllegalArgumentException("Either source or target does not exist");
        }

        BeanWrapper srcWrapper = new BeanWrapperImpl(src);
        BeanWrapper targetWrapper = new BeanWrapperImpl(target);

        PropertyDescriptor[] srcDescriptors = srcWrapper.getPropertyDescriptors();

        Set<String> srcMap = new HashSet<>();
        for (PropertyDescriptor srcDescriptor: srcDescriptors) {
                srcMap.add(srcDescriptor.getName());
        }

        srcMap.forEach((a) -> {
            try {
                Object value = srcWrapper.getPropertyValue(a);
                if (value != null) {

                    PropertyDescriptor targetPd = targetWrapper.getPropertyDescriptor(a);
                    if (targetPd.getWriteMethod() != null) {
                        targetWrapper.setPropertyValue(a, value);
                    }
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Target does not contain attribute " + a);
            }
        });

    }

    public static void copyNonNullProperties(Object src, Object target, List<String> ignoredProperties) {
        if (src == null || target == null) {
            throw new IllegalArgumentException("Either source or target does not exist");
        }

        BeanWrapper srcWrapper = new BeanWrapperImpl(src);
        BeanWrapper targetWrapper = new BeanWrapperImpl(target);

        PropertyDescriptor[] srcDescriptors = srcWrapper.getPropertyDescriptors();

        Set<String> srcMap = new HashSet<>();
        for (PropertyDescriptor srcDescriptor: srcDescriptors) {
            if (!ignoredProperties.contains(srcDescriptor.getName())) {
                srcMap.add(srcDescriptor.getName());
            }
        }

        srcMap.forEach((a) -> {
            try {
                Object value = srcWrapper.getPropertyValue(a);
                if (value != null) {

                    PropertyDescriptor targetPd = targetWrapper.getPropertyDescriptor(a);
                    if (targetPd.getWriteMethod() != null) {
                        targetWrapper.setPropertyValue(a, value);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalArgumentException(e.toString());

            }
        });

    }
}

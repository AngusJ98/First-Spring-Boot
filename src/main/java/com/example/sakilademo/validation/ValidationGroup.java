package com.example.sakilademo.validation;

import jakarta.validation.groups.Default;


public final class ValidationGroup {
    public interface Create extends Default{}
    public interface Update extends Default{}
}

package ru.antipant.domain;

public enum NavigationItem {
    Prod("Продукты"), Sol("Решения");

    public final String rusName;

    NavigationItem(String rusName) {
        this.rusName = rusName;
    }
}

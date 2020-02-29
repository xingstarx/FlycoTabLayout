package com.flyco.tablayout.listener;


public class ImageTabEntity implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;
    public String selectedIconStr;
    public String unSelectedIconStr;
    //直接用本地资源时候用的构造方法
    public ImageTabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }
    //用url时的构造方法，这两个构造方法里的第一个参数就是选项的字，第二个参数是选项被选中时的图，第三个参数是选项未被选中时的图
    public ImageTabEntity(String title,String selectedIconStr,String unSelectedIconStr){
        this.title = title;
        this.selectedIconStr = selectedIconStr;
        this.unSelectedIconStr = unSelectedIconStr;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }

    @Override
    public String getTabSelectedIconByString() {
        return selectedIconStr;
    }

    @Override
    public String getTabUnSelectedIconByString() {
        return unSelectedIconStr;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSelectedIcon(int selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public void setUnSelectedIcon(int unSelectedIcon) {
        this.unSelectedIcon = unSelectedIcon;
    }

    public void setSelectedIconStr(String selectedIconStr) {
        this.selectedIconStr = selectedIconStr;
    }

    public void setUnSelectedIconStr(String unSelectedIconStr) {
        this.unSelectedIconStr = unSelectedIconStr;
    }
}

## ViewPagerIndicator

### 1、截图

电脑触摸板操作模拟器不方便，所以截图有卡顿效果，真机上没问题。

![](screenshot.gif)


### 2、页面切换不经过中间页面

自定义ViewPager重写setCurrentItem方法

 	@Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }
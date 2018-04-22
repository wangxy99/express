-------------------
本程序用于将顺丰的转单号查询UPS的运单号
由jdk1.7+Maven+Eclipse Oxygen.3 Release (4.7.3)开发，
打包方式:
在工程名上右键--Run As--Maven bild...--Goals: package
-------------------


当前版本 V2.0.0 内容说明：
 增加显示进度
    实现方案：
    	以新线程调用 SearchRunnable 查询单据信息；
    	用定时器启用新线程调用 ProgressCallable 获取当前进度。
    	两个线程传入同一个 QueryService 为参数，共享给两个线程使用，只有当线程共用同一个参数才能取到进度信息。
    	如果传入一个 final QueryService queryService,在每一个线程中的queryService都会是新的实例。
    	
  未解决的问题：
  	1. 点击"中止"按钮时，定时器停止了，但 SearchThread 线程调用 interrupt() 方法每次都报错。查询会持续运行。
  	2. 查询进度时每次都是启用一个新的线程，总感觉有点不太好，能否只用一个线程，查询一次后等待，当下一次定时器触发又继续查询
  	3. 进度条超过一半时中间的字符颜色看不清

版本 V1.0.1 内容说明：
 1. 第一个查询地址失效
 2. 第二个查询地址数据结构改变，重新调整查询方法

版本 V1.0.0 内容说明：
 1. 使用JFrame开发的GUI程序。
 2. 使用HttpClient访问两个网站获取数据
 3. 使用POI将数据写出到excel中
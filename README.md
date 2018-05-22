# Spring-Boot Block-Chain Demo 
## 基于Spring-Boot 2.x的区域链示例


参考自 [Learn Blockchains by Building One](https://hackernoon.com/learn-blockchains-by-building-one-117428612f46 "Title") , 中文翻译：[用Python从零开始创建区块链](https://www.cnblogs.com/tinyxiong/p/7761026.html)

--- 
 
原文是基于python语言的，本示例改用spring-boot(2.x版本)重写了一遍。

由于spring-boot 自2.x起，要求gradle 4.x才能正常编译，所以大家调试时，请先确认本机gradle版本。


---
运行方式:

 1.  在idea/eclipse中run com.cnblogs.yjmyzz.blockchain.BlockChainHelloWorldApplication 即可
 2.  也可以直接用gradle bootRun 将会在8080端口启动，另外为了演示多节点集群，可以同时再运行gradle 8081 bootRun 在8081端口再启动一个实例

---

 swagger文档：

 启动后，浏览http://localhost:8080/swagger-ui.html 就可以看到文档，可以直接在线调用(类似postman)

---

测试步骤：

 1. 先启用2个实例，分别是8080,8081端口
 2. 在8080端口上调用/mine挖矿，挖2个区块出来
 3. 调用/chain查看整个链
 4. 调用/transactions/new 创建一条新交易，注意recepient必须是某个有效区块的hash值
 5. 调用/validate验证整个区块链的有效性
 6. 调用/register 将http://localhost:8081/ 注册到8080实例上，相当于模拟集群环境
 7. 在8081上调用/resolve观察，是否会将本节点上的区块链，更换成8080的更长的链

---


by [菩提树下的杨过](http://yjmyzz.cnblogs.com/) @2018
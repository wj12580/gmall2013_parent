package com.atguigu.gmall2013.gmall0213_logger.cotroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;





/**controller就是最外层负责接收web请求的代码就起名叫controller
 * @author wj
 * @create 2020-07-15 18:39
 */
@RestController //@RestController=@controller+@response
public class LoggerController {

    @Autowired
            //spring boot 整合了kafka kafka集群地址是在配置文件中指定的
    KafkaTemplate kafkaTemplate;
     @RequestMapping("/applog")//是描述请求路径的，和application.properties配置文件中的
    // mock.url=http://192.168.3.1:8090/applog一致。因为web默认的端口是8080，这里的端口我们改成了8090



     //传递参数
     //1.如果参数放到路径里：http://localhost:8090/applog?name=zhangsan
            //那么applog(@RequestParam("name"))
     //2.如果放到请求体中：没有后边的 ? ，取数据应该用applog(@RequestBody)
    public String applog(@RequestBody String json){
        System.out.println(json);

        //将json字符串转化成相当于Java中的集合
        JSONObject jsonObject = JSON.parseObject(json);


        //将启动日志和其他的日志数据分开
        if(jsonObject.getString("start") != null && jsonObject.getString("start").length()>0){
            kafkaTemplate.send("GMALL_START0213",json);
        }else{
            kafkaTemplate.send("GMALL_EVENT0213",json);
        }
        return "success";
    }


    //测试能否访问
//    @RequestMapping("/applog")
//    public String applog(){
//        return "success";
//    }
}

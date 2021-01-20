package main.java.threadPoolExecutor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-20
 * @Description: 接口性能优化：
 * 整个过程是按顺序执行的，实际上3个查询之间是没有任何依赖关系，可以同时执行，对这3个步骤采用多线程并行执行
 * 1、先列出无依赖的一些操作
 * 2、 将这些操作改为并行的方式
 */
public class Demo4 {
    /**
     * 获取商品基本信息
     *
     * @param goodsId 商品id
     * @return 商品基本信息
     * @throws InterruptedException
     */
    public String goodsDetailModel(long goodsId) throws InterruptedException {
        //模拟耗时，休眠200ms
        TimeUnit.MILLISECONDS.sleep(200);
        return "商品id:" + goodsId + ",商品基本信息....";
    }

    /**
     * 获取商品图片列表
     *
     * @param goodsId 商品id
     * @return 商品基本信息
     * @throws InterruptedException
     */
    public List<String> goodsImgsModelList(long goodsId) throws InterruptedException {
        //模拟耗时，休眠200ms
        TimeUnit.MILLISECONDS.sleep(200);
        return Arrays.asList("图1", "图2", "图3");
    }

    /**
     * 获取商品描述信息
     *
     * @param goodsId 商品id
     * @return 商品描述信息
     * @throws InterruptedException
     */
    public String goodsExtModel(long goodsId) throws InterruptedException {
        //模拟耗时，休眠200ms
        TimeUnit.MILLISECONDS.sleep(200);
        return "商品id:" + goodsId + ",商品描述信息......";
    }

    //创建个线程池
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * 获取商品详情
     *
     * @param goodsId 商品id
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Map<String, Object> goodsDetail(long goodsId) throws ExecutionException, InterruptedException {
        Map<String, Object> result = new HashMap<>();


/*        result.put("goodsDetailModel", goodsDetailModel(goodsId));
        result.put("goodsImagesModelList", goodsImgsModelList(goodsId));
        result.put("goodsExtModel", goodsExtModel(goodsId));*/
        //异步获取商品基本信息
        Future<String> gooldsDetailModelFuture = executorService.submit(() -> goodsDetailModel(goodsId));
        //异步获取商品图片列表
        Future<List<String>> goodsImgsModelListFuture = executorService.submit(() -> goodsImgsModelList(goodsId));
        //异步获取商品描述信息
        Future<String> goodsExtModelFuture = executorService.submit(() -> goodsExtModel(goodsId));

        result.put("goodsDetailModel", gooldsDetailModelFuture.get());
        result.put("goodsImagesModelList", goodsImgsModelListFuture.get());
        result.put("goodsExtModel", goodsExtModelFuture.get());

        executorService.shutdown();

        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        Map<String, Object> map = new Demo4().goodsDetail(1L);
        System.out.println(map);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime));

    }


}

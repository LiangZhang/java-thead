package com.zlsoft.patten.producer;

import com.zlsoft.core.Sleeper;
import com.zlsoft.patten.Downloader;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Slf4j(topic = "c.ProducerConsumer")
public class ProducerConsumer {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(2);
        for (int i = 0; i < 4; i++) {
            int id = i;
            new Thread(() -> {
                try {
                    log.debug("download...");
                    List<String> response = Downloader.download();
                    log.debug("try put message({})", id);
                    messageQueue.put(new Message(id, response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, "生产者" + i).start();
        }

//        new Thread(()->{
//
//        }, "消费者").start();

        new Thread(() -> {
            while (true) {
                Sleeper.sleep(1);
                Message message = messageQueue.take();
                List<String> response = (List<String>) message.getMessage();
                log.debug("take message({}): [{}] lines", message.getId(), response.size());
            }

        }, "消费者").start();
    }
}

/**
 * 消息对象：
 *   -如果只有get方法，只有生成的时候构造，保证线程安全
 */
class Message {
    private int id;
    private Object message;

    public Message(int id, Object message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public Object getMessage() {
        return message;
    }
}

/**
 * rabbitMq：进程间的通信
 * MessageQueue：线程之间进行通信的消息队列
 */
@Slf4j(topic = "c.MessageQueue")
class MessageQueue {
    //用一个容器来存储消息队列
    private LinkedList<Message> queue;
    //消息队列一般都有容量，
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
        queue = new LinkedList<>();
    }

    /**
     * consumer获取消息
     * @return
     */
    public Message take() {
        //取消息：如果消息队列为null,则等待，这里用成员队列queue来
        synchronized (queue) {
            //检查队列是否为null,线程在哪里等待，在queue的waitSet上去等待，
            //必须为queue加synchronized
            while (queue.isEmpty()) {
                log.debug("没货了, 消费者线程 wait");
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //我们约定：取从头取，存从尾部存储
            Message message = queue.removeFirst();
            //通知生产者可以继续生产消息
            queue.notifyAll();
            return message;
        }
    }

    /**
     * producer生产消息，存入消息
     * @param message
     */
    public void put(Message message) {
        synchronized (queue) {
            //检查是否队列已经满，如果满了，让该线程在queue的waitset中去等待。
            while (queue.size() == capacity) {
                log.debug("库存已达上限, 生产者线程 wait");
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //消息加入队列尾部
            queue.addLast(message);
            //唤醒等待消息的线程
            queue.notifyAll();
        }
    }
}

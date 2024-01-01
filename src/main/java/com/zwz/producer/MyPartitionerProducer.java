package com.zwz.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class MyPartitionerProducer {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.18.16:9092");
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.zwz.producer.MyPartitioner");

        KafkaProducer<String, String> producer = new KafkaProducer<>(prop);
        producer.send(new ProducerRecord<>("first_topic", "zwz hello"), new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                System.out.println(String.format("send to topic %s, partition %d", metadata.topic(), metadata.partition()));
            }
        });

        producer.close();
    }
}

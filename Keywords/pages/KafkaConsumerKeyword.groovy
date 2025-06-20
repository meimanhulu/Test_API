package pages

import org.apache.kafka.clients.consumer.*
import org.apache.kafka.common.serialization.StringDeserializer
import com.kms.katalon.core.annotation.Keyword
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.Properties
import groovy.json.JsonSlurper
import java.util.Arrays

class KafkaConsumerKeyword {
   @Keyword
def consumeMessages() {
    Properties properties = new Properties()
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group")
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())

    Consumer<String, String> consumer = new KafkaConsumer<>(properties)

    try {
        consumer.subscribe(Arrays.asList("inventory-topic"))

        int messageCount = 0  
        long timeoutMillis = 5000  
        long startTime = System.currentTimeMillis()

      
        while (true) {
   
            if (System.currentTimeMillis() - startTime > timeoutMillis) {
                println("Timeout reached. Stopping consumer.")
                break 
            }

            def records = consumer.poll(1000) 

            if (!records.isEmpty()) {
                records.forEach { record ->
                    println("Consumed record with key " + record.key() + " and value " + record.value())
                    messageCount++
                    if (messageCount == 1) {
                        GlobalVariable.firstItemId = record.key()  
                        GlobalVariable.firstItemName = record.value() 
                    }
                }
            }

     
            if (messageCount >= 10) {
                println("Consumed 10 messages. Stopping consumer.")
                break  
            }
        }
    } catch (Exception e) {
        println("Error: Unable to connect to Kafka Broker or consume messages. ${e.message}")
    } finally {
        consumer.close()
        println("Kafka Consumer connection closed.")
    }
}
}

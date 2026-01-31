package com.verxina.transaction.producer;

import com.verxina.common.event.TransactionCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${app.kafka.topic.transaction}")
    private String topicName;

    public void sendTransactionEvent(TransactionCreatedEvent event) {
        log.info("Sending event to Kafka {}", event);
        kafkaTemplate.send(topicName, event.transactionId(), event);
    }
}
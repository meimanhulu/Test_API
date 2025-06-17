from kafka import KafkaProducer
import json

# Membuat Kafka Producer
producer = KafkaProducer(
    bootstrap_servers='localhost:9092',  # Kafka broker di localhost
    value_serializer=lambda v: json.dumps(v).encode('utf-8')  # Mengonversi data ke JSON dan encoding UTF-8
)

# Data untuk dikirim ke Kafka
message = {
    'id': 'c413',
    'name': 'newItem'
}

# Mengirim pesan ke Kafka topic "inventory-topic"
producer.send('inventory-topic', message)
producer.flush()  # Menyelesaikan pengiriman pesan
print("Pesan terkirim ke Kafka.")

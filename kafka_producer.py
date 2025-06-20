from kafka import KafkaProducer
import time

# Inisialisasi Kafka producer
producer = KafkaProducer(bootstrap_servers='localhost:9092')

# Fungsi untuk mengirim pesan ke Kafka topic
def send_messages():
    for i in range(10):
        message = f"Message {i}"
        producer.send('inventory-topic', value=message.encode('utf-8'))
        print(f"Sent: {message}")
        time.sleep(1)  # Delay 1 detik antara pengiriman pesan

# Kirim pesan
send_messages()

# Tutup producer
producer.close()

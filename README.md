# Tanum Android App

## Deskripsi
Tanum adalah aplikasi yang memungkinkan petani mencatat lahan pertanian, mengelola aktivitas lahan, dan mengakses berita terkini tentang pertanian di Indonesia.

## Fitur
- **Homepage**: Pada Homepage, pengguna akan menemukan highlight mengenai lahan yang telah mereka catat dan artikel terkait pertanian di Indonesia.

- **Lahan Saya**: Di halaman "Lahan Saya," pengguna dapat mengakses daftar lahan yang mereka kelola. Mereka juga memiliki kemampuan untuk menambahkan lahan baru, mengedit lahan yang sudah ada, dan mencatat aktivitas yang terkait dengan setiap lahan.

- **Artikel**: Pada fitur "Artikel," pengguna dapat menemukan berita dan artikel terkini mengenai perkembangan terbaru dalam dunia pertanian di Indonesia.

## Persyaratan Sistem
- Android 8.0 (Oreo) atau versi di atasnya
- Koneksi internet stabil untuk beberapa fitur

## Instalasi
1. Clone repositori ke mesin lokal Anda.
2. Buka Android Studio.
3. Bangun dan sinkronkan proyek.
4. Hubungkan perangkat Android Anda atau mulai emulator.
5. Jalankan aplikasi pada perangkat yang terhubung atau emulator.

## Library dan Framework
1. **Retrofit**: Library HTTP client untuk Android dan Java yang memudahkan pemanggilan API REST. Dengan menggunakan Retrofit, Anda dapat dengan mudah melakukan pengambilan data dari server dan mengintegrasikannya ke dalam aplikasi Android Anda.

2. **Glide**: Library pengelola gambar yang kuat dan efisien untuk Android. Digunakan untuk memuat dan menampilkan gambar dengan mudah, serta menyediakan fitur caching yang canggih untuk meningkatkan kinerja aplikasi.

3. **Room**: Framework pengelola database SQLite untuk Android. Memberikan lapisan abstraksi di atas SQLite dan memfasilitasi operasi database dengan menggunakan Java/Kotlin.

4. **Paging 3**: Library Android Jetpack untuk memfasilitasi pemuatan data sejilid demi sejilid di aplikasi Android, memungkinkan pengelolaan data yang besar secara efisien.

5. **Kotlin Coroutines**: Library untuk pemrograman konkuren asynchronous di Kotlin. Digunakan untuk menangani tugas-tugas async tanpa perlu menggunakan callback atau blocking thread secara langsung.

## Arsitektur Proyek

Proyek ini menggunakan arsitektur Model-View-ViewModel, yang didesain untuk meningkatkan keterbacaan kode, skalabilitas, dan pemeliharaan. Berikut adalah komponen utama dari arsitektur ini:

1. **Model-View-ViewModel (MVVM):**
   - Penyusunan logika bisnis dan tampilan untuk memisahkan tanggung jawab.
   - Model merepresentasikan data dan aturan bisnis.
   - View bertanggung jawab untuk menampilkan data dan menerapkan antarmuka pengguna.
   - ViewModel mengelola logika bisnis dan komunikasi antara Model dan View.

2. **Repository Pattern:**
   - Menerapkan repository untuk mengelola akses ke data.
   - Memisahkan logika akses data dari sumber data dan tampilan.

3. **Dependency Injection:**
   - Menggunakan singleton object untuk mengelola ketergantungan dan meningkatkan fleksibilitas aplikasi.

## Struktur Proyek

Struktur proyek mengikuti pola yang didefinisikan oleh arsitektur MVVM. Berikut adalah beberapa komponen utama:

- **/app**
   - **/src/main/java/com/tanum/app**
        - **/adapter**: Kelas adapter untuk Recycler View dan Paging 
        - **/di**: Konfigurasi Dependency Injection.
        - **/utils**: Kelas-kelas fungsi bantuan 
        - **/model**: Kelas-kelas model data.
        - **/view**: Komponen-komponen tampilan dan aktivitas.
        - **/viewmodel**: Kelas-kelas ViewModel.
      


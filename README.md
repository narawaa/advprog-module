Pemrograman Lanjut (Advanced Programming) 2024/2025 Genap
* Nama    : Nashwa Ghania
* NPM     : 2306241770
* Kelas   : Pemrograman Lanjut - A

Link Deployment: [ADVShop](https://advprog-module-narawaa.koyeb.app)

<details>
<summary>Module 1</summary>

### Reflection 1
**Clean Code Principles yang Sudah Diterapkan**
1. **Single Responsibility Principle (SRP)**: Setiap kelas memiliki tanggung jawab masing-masing: `ProductController` 
untuk menangani request, `ProductService` untuk bisnis logic, dan `ProductRepository` untuk penyimpanan data.
2. **Meaningful Names**: Nama variabel dan method sudah jelas dan sesuai dengan fungsinya.
3. **Kode yang Rapi**: Format kode terstruktur dengan baik.

**Secure Coding Practices yang Sudah Diterapkan**
1. **Model Binding dengan `@ModelAttribute`**: Mengurangi manipulasi data manual saat menerima input dari form.
2. **UUID untuk Identifikasi Produk**: Menggunakan UUID agar lebih aman dibandingkan auto-increment integer.

**Kesalahan dan Perbaikannya**<br/>
Disini masih menggunakan GET atau POST saja, seharusnya bisa disesuaikan dengan menggunakan method lain seperti PUT dan 
DELETE.

### Reflection 2
1. Tidak ada aturan pasti tentang jumlah unit test dalam satu class, yang penting adalah menguji setiap method dengan 
berbagai skenario. Untuk memastikan unit test cukup, kita bisa menggunakan code coverage sebagai metrik, tapi 100% code 
coverage tidak menjamin kode bebas dari bug, karena bisa saja masih ada logical errors yang tidak ketahuan jadi cakupan 
pengujian harus dipastikan mencakup semua skenario penting, bukan hanya mengejar angka coverage.


2. Menyalin kode dari test suite sebelumnya bisa berdampak buruk pada kualitas kode.
Masalah utama dalam menyalin kode dari test suite sebelumnya adalah duplikasi kode, yang dapat menyulitkan pemeliharaan 
karena setiap perubahan di setup harus dilakukan di banyak tempat secara manual. Selain itu, kode menjadi kurang 
fleksibel, semakin banyak test suite yang memiliki kode berulang, semakin sulit melakukan penyesuaian tanpa mempengaruhi
bagian lain. Hal ini juga berdampak pada keterbacaan kode, di mana pengulangan yang berlebihan membuat test suite lebih 
panjang dan tidak efisien. Untuk mengatasi ini, sebaiknya menggunakan base test class agar setup dapat digunakan ulang 
tanpa harus menyalin kode di setiap test suite. Selain itu, parameterized tests dapat digunakan untuk menghindari 
pengulangan test case yang memiliki pola serupa.

</details>

<details>
<summary>Module 2</summary>

**1. Code quality issue yang telah diperbaiki**<br/>
- Menghapus modifier public pada interface. <br/>
Sebelumnya, ProductService interface memiliki modifier public, yang sebenarnya tidak perlu ditulis lagi. Secara 
default, method yang ada di dalam interface sudah bersifat public, jadi modifier tersebut bisa dihapus untuk membuat 
kode lebih bersih.<br/><br/>

- Menambahkan komentar untuk method kosong. <br/>
Method setUp() dalam unit test dibiarkan kosong tanpa penjelasan. Untuk menghindari kebingungan, ditambahkan komentar 
yang menjelaskan alasan mengapa method ini kosong.<br/><br/>

- Mengubah createProduct menjadi CreateProduct. <br/>
Sebelumnya, return value pada method di ProductController menggunakan createProduct, sementara file HTML yang 
merendernya bernama CreateProduct. Untuk konsistensi dan menghindari error, return tersebut diubah menjadi 
CreateProduct. Beberapa return lain yang sebelumnya diawali huruf kecil juga diperbaiki menjadi kapital sesuai dengan 
nama file HTML-nya.<br/><br/>

**2. Apakah CI/CD yang digunakan sudah sesuai dengan definisi Continuous Integration dan Continuous Deployment?**<br/>
YEP! Saat ini, CI/CD sudah berjalan dengan baik menggunakan ci.yml, sonarcloud.yml, dan scorecard.yml. CI berjalan 
otomatis setiap kali ada perubahan kode, memastikan semua perubahan diuji sebelum digabung ke branch main. SonarCloud 
digunakan untuk analisis kode, menjaga kualitasnya agar tetap sesuai standar dan mencegah potensi masalah di production. 
Deployment dilakukan otomatis setiap kali perubahan sudah divalidasi, memastikan aplikasi selalu dalam kondisi terbaru 
tanpa perlu proses manual. Dengan demikian, implementasi CI/CD ini sudah cukup sesuai dengan definisinya.

</details>

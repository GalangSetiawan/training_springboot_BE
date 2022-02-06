Tugas untuk Spring boot - Angular, dengan template yang sudah menyerupai riil project :

   masih toko buku, dengan tambahan/perubahan :
   1. menggunakan template BE yang sudah riil
      jadi harus mengkonversi apa yang sudah dibuat di tugas springboot ke tempate BE
      yang diberikan untuk tugas ini, karena template BE ini sudah riil
      (dalam tugas ini peserta sudah berperan sebagai full stack developer)
     
   2. tambahan di desain struktur data yang harus ada :
      a. header transaksi harus memiliki id jenis transaksi
         jenis transaksi ini dimasterkan tapi tidak ada inputnya (alias Sofco Defined)
         contoh jenis transaksi :
         ID        Kode          Nama transaksi
         1         TKB01         Transaksi Pembelian Buku
       
      b. tabel yang menyatakan berapa rupiah mendapatkan berapa poin
         contoh :
         Range              Jumlah Poin
         0     s/d 10000        10
         10001 s/d 25000        15
         25001 s/d 50000        20
         > 50001                25
       
      c. tabel saldo kas titipan dan poin per member
         (silakan dipikirkan field apa saja yang harus ada di tabel ini)
     
   3. tambahan spesifikasi :
   
      a. membership
         ada menu input membership
         
      b. di transaksi pembelian buku
         o pembeli bisa member bisa non member,
           bila member harus terdaftar di membership
           bila bukan member hanya input nama pembeli dalam bentuk teks biasa

         o bila pembeli adalah member maka   
           - dapat menikmati promo 5 pembeli pertama gratis buku tulis
           - mendapat poin untuk pembelian sekian rupiah (syarat lihat tabel poin)
           - mendapat fasilitas kas titipan, untuk kelebihan bayar tunai
           - dapat menggunakan poin dan kas titipan sebagai pembayaran

         o di header transaksi pembelian
           - memiliki data total jumlah harus dibayar
           - bila detail pembayaran ada menggunakan tunai
             maka bisa jadi ada kembalian uang (bila bukan member), atau
             otomatis masuk ke saldo kas titipan (bila member)
           
      c. detail pembayaran
         o detail pembayaran bisa bertipe :
           tunai (uang tunai), debit, dompet digital, poin atau kas titipan
           pembeli yang bisa membayar dengan poin adalah pembeli yang sudah menjadi member
           
           NB: kurs 1 poin = berapa rupiah silakan dihard code di dalam program
           
         o bila salah satu metode pembayaran adalah poin, maka transaksi pembelian
           tersebut tidak bisa dapat poin lagi
           
         o bila metode pembayaran di detail pembayaran tidak ada yang bertipe poin,
           bila status pembeli adalah member, ia akan mendapat tambahan saldo poin atas
           pembelian tsb
           
   4. Catatan terkait front end :
   
      a. input pembeli di header menggunakan autocomplete  
      b. input kode buku di detail ada fasilitas info
         dengan filter : judul buku dan jenis buku
      c. input tipe pembayaran di detail pembayaran berupa combo
         NB: item-item combo silakan di-hard code
         
   5. Usahakan utk memperhatikan UI/UX, jadi input usahakan seinformatif mungkin
   
   6. laporan penjualan buku per tahun (kolom januari s/d desember, terisi dengan jumlah buku terjual)

Terima kasih,

Henrie Prawiro

JAWABAN ESSAY - PENGGUNAAN AI DALAM PEKERJAAN
Rois Hoiron

Soal 1 - Peta Kerja Nyata

Fitur yang saya ambil sebagai contoh adalah fitur scan kulkas untuk rekomendasi resep berdasarkan bahan yang terdeteksi, di aplikasi ChefGenie (Flutter, cross-platform).

Alur kerja saya dimulai dari memahami sisi bisnis dulu tanpa AI - semacam blind user test terhadap alur pakai fitur ini, lalu mendalami dokumen bisnis (BRD/PRD/FSD kalau tersedia) dan berdiskusi dengan pemilik dokumen tersebut. Ini penting supaya saya benar-benar paham konteks sebelum mulai setup AI workflow - mulai dari pemilihan model sampai tools untuk memahami codebase (saya pakai pendekatan seperti Graphify untuk memetakan codebase jadi knowledge graph agar AI tidak perlu membaca seluruh file, dan prinsip Superpowers: brainstorm - plan - approve - TDD - implement - verify - document, di mana setiap tahap harus benar-benar dijalani, tidak boleh dilompati).

Untuk deteksi gambar bahan makanan, saya memakai Gemini. Akurasi deteksinya cukup baik, sekitar 90% kecocokan terhadap dataset. Masalah yang muncul: di beberapa device dengan kualitas kamera terbatas, gambar yang diupload mengalami glitch sehingga tidak utuh - akibatnya confidence score deteksi turun di bawah ambang 90% yang saya tetapkan.

Solusi yang saya buat: kalau confidence score AI tidak mencapai ambang kemiripan dataset (90%), sistem akan mengorkestrasi user untuk lanjut ke Gemini Live, supaya user bisa mengonfirmasi langsung lewat percakapan suara bahan apa saja yang ada di gambar tersebut. Jadi input gambar tidak dipaksakan jadi satu-satunya jalur - ada fallback percakapan yang sekaligus berfungsi sebagai koreksi dari user (domain expert level 1) terhadap hasil model.

Bagian yang saya kerjakan sepenuhnya sendiri tanpa AI: code review menyeluruh dari histori git commit, serta testing - bukan cuma unit test, tapi juga integration test - untuk memastikan tidak ada regresi kondisi test yang tadinya pass jadi failed. Ini saya pegang sendiri karena menentukan skenario mana yang benar-benar mewakili risiko bisnis adalah keputusan yang tidak bisa saya delegasikan ke AI.

Untuk instruksi/prompt, saya tidak terpaku pada prompt manual per kasus - saya membangun agent sendiri untuk mengelola alur kerja development, salah satu contohnya bisa dilihat di implementasi saya di github.com/codingskuy/coding-school (file engineering.ts).


Soal 2 - Ketika AI Salah

Kejadian ini terjadi pada fitur scan kulkas di ChefGenie, yang menggunakan Gemini untuk deteksi gambar bahan makanan. AI menyarankan pendekatan deteksi gambar sebagai satu-satunya jalur input, dengan asumsi kualitas gambar dari kamera device akan selalu cukup baik untuk diproses. Ini ternyata salah - di beberapa merek device dengan kamera terbatas, gambar yang diupload mengalami glitch sehingga tidak utuh, dan hasil deteksi meleset dari yang seharusnya.

Saya menyadarinya bukan dari crash atau error saat build, tapi dari confidence score deteksi yang saya pantau - score-nya turun signifikan di bawah ambang 90% yang saya tetapkan sebagai baseline kecocokan dataset, khusus untuk kasus-kasus dengan gambar dari device tertentu. Dari situ saya telusuri dan ketahuan gambar yang masuk ke model memang cacat di level device, bukan di level model AI-nya.

Waktu yang terbuang sekitar 1 jam, mencakup proses fixing sampai looping ulang testing untuk fitur ini.

Setelah kejadian ini, saya menambahkan confidence threshold sebagai standar wajib untuk setiap fitur berbasis AI vision yang saya kerjakan - jadi sistem tidak akan langsung mempercayai output model, melainkan mengecek dulu apakah confidence-nya memenuhi ambang tertentu. Kalau tidak, sistem mengorkestrasi fallback (dalam kasus ini ke Gemini Live untuk konfirmasi suara) alih-alih meneruskan hasil deteksi yang kualitasnya diragukan.


Soal 3 - Membuat AI Nyambung dengan Codebase yang Sudah Ada

Cara memberi konteks codebase ke AI:
Saya menggunakan Graphify untuk membangun knowledge graph dari codebase - ini memetakan hubungan antar file, misalnya kalau file A1 berubah maka AI agent tahu file A0 ikut terdampak, sehingga konsekuensi perubahan bisa dipetakan sebelum kode digenerate. Sebagai pelengkap, untuk project besar dan termodulasi, saya pakai vector DB untuk embedding - biasanya Qdrant untuk kebutuhan yang lebih ringan, atau Pinecone untuk project skala besar. Jadi AI tidak hanya membaca kode secara literal, tapi juga punya representasi relasi dan semantik dari codebase yang bisa di-query sesuai kebutuhan task, tanpa harus membaca ulang seluruh file setiap kali.

Yang paling sering membuat output AI "tidak nyambung" padahal kodenya sendiri tidak salah:
Menurut pengalaman saya, akar masalahnya sering di instruksi yang terlalu singkat dan tidak presisi. Contohnya prompt seperti "tulis kode dengan TDD + clean architecture" - ini bisa menghasilkan kode yang secara teknis valid dan memenuhi kaidah clean architecture, tapi AI cuma mengikuti flow layering-nya saja tanpa benar-benar memahami unsur utama dari kaidah tersebut sesuai konvensi project. Selain itu ada limitasi context window dan level kecerdasan model dari masing-masing provider yang membuat pemahaman terhadap struktur project jadi tidak utuh. Karena itu saya banyak belajar ilmu QA - supaya saya punya kerangka evaluasi sendiri terhadap hasil AI, bukan cuma mengandalkan model untuk paham konvensi dengan sendirinya.

Saat AI 2-3 kali meleset di masalah yang sama:
Kasus paling sering saya alami adalah saat sebuah library baru saja update secara major - AI model biasanya belum punya pemahaman soal perubahan tersebut karena data training-nya sudah usang. Solusi awal saya adalah mengarahkan AI ke MCP seperti Context7 atau referensi dokumentasi resmi (GitHub repo/Google knowledge) supaya AI bisa "belajar" dari sumber terkini saat itu juga. Tapi ini enggak selalu cukup - karena AI biasanya hanya mengandalkan satu sumber eksternal tanpa proses weight calculation yang matang, jadi rawan berhalusinasi mencampur pemahaman data lama dan baru secara tidak utuh. Kalau sudah sampai titik ini - AI tetap meleset meski sudah dikasih dokumentasi terbaru - saya berhenti minta ulang dan perbaiki sendiri secara manual, lalu memberi tahu AI apa yang sudah saya ubah supaya konteks berikutnya tetap sinkron.

Siklus perbaikan AI yang menimbulkan masalah baru berulang:
Cara saya keluar dari siklus ini adalah lewat regression test dalam alur TDD. Setiap AI generate perubahan kode, saya bandingkan laporan hasil test sebelum dan sesudah perubahan tersebut dijalankan. Feedback dari regression test ini cukup efektif mencegah AI terjebak di infinite loop perbaikan, karena ada sinyal objektif kapan sebuah fix benar-benar menyelesaikan masalah tanpa memunculkan regresi baru.

Jenis task yang hasilnya konsisten bagus vs buruk:
Konsisten bagus: unit test - karena konteks yang dibutuhkan relatif kecil dan terisolasi per fungsi/komponen. Konsisten buruk: UI test atau golden test - karena butuh konteks jauh lebih besar, baik dari sisi codebase maupun resource MCP yang harus diproses. Ini bisa diminimalisir dengan penerapan atomic design yang baik, karena pengetesan tampilan bisa dipecah lebih modular per komponen, sehingga konteks yang dibutuhkan AI di setiap task testing jadi lebih kecil dan terarah.


Soal 4 - Kerahasiaan dan Keamanan

Untuk proyek yang menangani data sensitif, saya tidak menganggap bahwa sebuah tool aman hanya karena tool tersebut open-source atau populer. Saya tetap melihat bagaimana tool tersebut menangani data, di mana data diproses, apakah ada mekanisme guardrails, dan bagaimana risiko kebocoran dapat dikendalikan.

Saya juga menerapkan prinsip data minimization ketika menggunakan AI. Informasi seperti API key, access token, credential, data pengguna, data pasien, data transaksi, maupun source code proprietary yang tidak diperlukan untuk menyelesaikan masalah tidak saya masukkan ke layanan AI publik.

Untuk workflow AI development, saya lebih memilih menggunakan konteks yang sudah diminimalkan. Misalnya ketika meminta AI membantu debugging, saya cukup memberikan struktur atau potongan kode yang relevan dan menghilangkan credential maupun informasi sensitif yang tidak diperlukan.

Salah satu cara yang menurut saya realistis untuk tetap memakai AI dalam kondisi kerahasiaan ketat adalah menempatkan AI gateway/proxy internal di dalam security boundary perusahaan. Jadi bukan sekadar "pakai model lokal", tapi semua permintaan ke AI - baik ke model internal maupun API eksternal - melewati layer proxy ini terlebih dahulu, di mana data sensitif bisa di-filter, di-redact, atau diblokir sebelum sempat keluar, sekaligus memungkinkan logging dan audit trail terhadap apa saja yang dikirim dan diterima dari AI. Konsekuensinya jelas: privasi dan kontrol naik, tetapi biaya infrastruktur, effort maintenance, serta kompleksitas setup jadi lebih berat, dan kalau modelnya juga di-hosting internal, kualitasnya biasanya di bawah model publik terbaru. Menurut saya trade-off ini sepadan untuk proyek dengan kontrak kerahasiaan ketat seperti sektor finansial, karena risiko kebocoran data jauh lebih mahal daripada biaya infrastruktur dan maintenance tersebut.

Selain guardrails pada aplikasi AI, saya juga berhati-hati dalam memilih tool AI yang digunakan dalam development. Saya tidak langsung menggunakan sebuah tool hanya karena tool tersebut gratis atau open-source. Salah satu referensi yang saya gunakan untuk melakukan screening awal adalah AI Security & Safety Directory, yang menyediakan daftar tool AI security dan safety beserta kategorinya, seperti guardrails, privacy, monitoring, prompt-injection defense, evaluation, dan vulnerability scanning. Directory tersebut sendiri mencantumkan baik tool open-source maupun komersial.

Namun, saya tidak menganggap keberadaan sebuah tool di directory tersebut sebagai jaminan bahwa tool tersebut otomatis aman. Bagi saya, itu hanya salah satu sinyal awal. Saya tetap perlu memahami arsitektur, data flow, permission, deployment model, dan risiko dari tool tersebut sebelum memasukkannya ke workflow development.

Untuk pengalaman konkret membatasi penggunaan tool karena insiden keamanan, saya belum pernah mengalami kejadian di mana saya harus menghentikan penggunaan suatu tool karena terjadi kebocoran data. Karena itu saya tidak ingin membuat contoh seolah-olah pernah mengalaminya.

Yang sudah saya lakukan adalah menerapkan kehati-hatian tersebut secara preventif: membatasi informasi sensitif yang masuk ke AI, menggunakan guardrails ketika relevan, dan melakukan evaluasi terhadap tool sebelum digunakan. Bagi saya, dalam konteks proyek finansial, lebih baik kehilangan sedikit kenyamanan atau kecepatan development daripada mengambil risiko source code atau data klien keluar dari lingkungan yang seharusnya.


Soal 5 - Batas dan Ketergantungan

a. Bagian yang menurut saya belum bisa diserahkan sepenuhnya kepada AI

Menurut pengalaman saya, setidaknya ada dua bagian yang masih harus dipegang developer.

Pertama adalah engineering judgment dan pemahaman terhadap kebutuhan bisnis. AI sangat membantu dalam mencari alternatif solusi dan menghasilkan implementasi, tetapi saya tidak menyerahkan keputusan akhir mengenai behavior aplikasi kepada AI. Developer tetap harus memahami masalah pengguna, constraint project, trade-off teknis, dan menentukan apakah sebuah solusi benar-benar tepat. Contohnya pada fitur scan kulkas di ChefGenie. AI dapat membantu melakukan analisis gambar, tetapi ketika saya menemukan bahwa kualitas gambar pada beberapa device dapat menyebabkan hasil deteksi tidak reliable, keputusan untuk tidak memaksakan image recognition dan menyediakan fallback melalui Gemini Live merupakan keputusan engineering yang saya ambil sendiri.

Kedua adalah validasi dan ownership terhadap hasil. AI dapat membantu membuat unit test, integration test, assertion, atau bahkan menganalisis kemungkinan bug. Tetapi menentukan apakah test tersebut benar-benar mewakili risiko aplikasi dan apakah implementasi tersebut aman untuk masuk production tetap menjadi tanggung jawab saya.

Saya melihat AI sebagai assistant yang dapat mempercepat pekerjaan, bukan sebagai pihak yang bertanggung jawab terhadap hasil akhirnya. Kalau hasilnya salah, yang bertanggung jawab tetap developer.

Saya juga menyadari bahwa semakin sering menggunakan AI, semakin besar risiko developer kehilangan pemahaman terhadap fundamental. Karena itu saya membangun CodingSchool, sebuah learning environment yang saya gunakan untuk mendorong proses belajar yang tetap berorientasi pada pemahaman dan kemampuan engineering, bukan hanya menghasilkan kode melalui AI.

b. Jika seluruh AI tool yang biasa saya gunakan tidak bisa diakses

Kalau mulai besok seluruh AI tool yang biasa saya gunakan tidak dapat diakses, saya akan tetap bisa mengerjakan pekerjaan sebagai developer, tetapi kecepatan saya akan turun cukup signifikan pada beberapa jenis task.

Dampak terbesar saya perkirakan terjadi pada pekerjaan seperti eksplorasi solusi, membaca dokumentasi library yang baru, membuat boilerplate, mencari kemungkinan penyebab bug, dan pekerjaan repetitif. Untuk task-task tersebut, AI biasanya mempercepat proses eksplorasi dan mengurangi waktu yang saya habiskan untuk mencari informasi secara manual.

Untuk pekerjaan seperti memahami requirement, membaca dan memodifikasi codebase, debugging berdasarkan log dan behavior aplikasi, testing, code review, serta mengambil keputusan engineering, dampaknya relatif lebih kecil karena proses tersebut tetap saya lakukan sendiri dan tidak saya delegasikan sepenuhnya kepada AI.

Saya tidak akan mengatakan bahwa hilangnya AI tidak berpengaruh. Saya memperkirakan produktivitas saya dapat turun sekitar 30-50% untuk task tertentu yang sangat terbantu oleh AI, terutama pada tahap eksplorasi dan implementasi awal. Namun saya tetap dapat melanjutkan pekerjaan secara manual menggunakan dokumentasi resmi, source code, git history, debugger, testing, dan pengalaman yang saya miliki.

Bagi saya, target penggunaan AI bukan membuat saya tidak bisa bekerja tanpa AI. Targetnya adalah membuat saya lebih cepat ketika AI tersedia, tetapi tetap kompeten ketika AI tidak tersedia.

import java.util.ArrayList; // Mengimpor ArrayList untuk menyimpan daftar pasien dalam antrian
import java.util.Scanner; // Mengimpor Scanner untuk membaca input dari pengguna

public class HospitalQueueSystemUts {
    private static ArrayList<Patient> patientQueue = new ArrayList<>(); // Membuat antrian pasien (ArrayList)
    private static Scanner scanner = new Scanner(System.in); // Membuat objek Scanner untuk input pengguna

    public static void main(String[] args) {
        boolean running = true; // Flag untuk menjalankan program (looping menu utama)

        System.out.println("Welcome to Hospital Queue Management System"); // Menampilkan pesan sambutan

        while (running) { // Selama program berjalan
            displayMenu(); // Menampilkan menu pilihan
            int choice = getValidIntInput("Masukkan Pilihann Anda: "); // Membaca input pilihan pengguna

            switch (choice) {
                case 1:
                    addPatient(); // Menambah pasien baru
                    break;
                case 2:
                    serveNextPatient(); // Melayani pasien berikutnya
                    break;
                case 3:
                    displayQueue(); // Menampilkan antrian pasien saat ini
                    break;
                case 4:
                    updatePriority(); // Memperbarui prioritas pasien
                    break;
                case 5:
                    searchPatient(); // Mencari pasien dalam antrian
                    break;
                case 6:
                    System.out.println("Thank you for using Hospital Queue Management System. Goodbye!"); // Pesan
                                                                                                          // keluar
                    running = false; // Menghentikan program
                    break;
                default:
                    System.out.println("Invalid choice. Please try again."); // Menampilkan pesan jika input tidak valid
            }
        }

        scanner.close(); // Menutup objek scanner setelah program selesai
    }

    private static void displayMenu() {
        // Menampilkan menu pilihan
        System.out.println("\n===== HOSPITAL QUEUE SYSTEM =====");
        System.out.println("1. Tambahkan pasien baru");
        System.out.println("2. Melayani pasien berikutnya");
        System.out.println("3. Menampilkan tampilan saat ini");
        System.out.println("4. Perbarui prioritas pasien");
        System.out.println("5. Mencari pasien");
        System.out.println("6. Keluar");
        System.out.println("=================================");
    }

    private static void addPatient() {
        System.out.println("\n--- Add New Patient ---"); // Pesan untuk menambah pasien baru
        String name = getValidStringInput("Masukkan Nama Pasien: "); // Input nama pasien
        int age = getValidIntInput("Masukkan umur pasien: "); // Input umur pasien
        String condition = getValidStringInput("Masukkan kondisi pasien: "); // Input kondisi pasien
        int priority = getValidIntInRange("Masukkan prioritas pasien (1-kritis hingga 5-tidak mendesak)): ", 1, 5); // Input
                                                                                                                    // prioritas

        Patient newPatient = new Patient(name, age, condition, priority); // Membuat objek pasien baru
        patientQueue.add(newPatient); // Menambahkan pasien ke dalam antrian

        patientQueue.sort((p1, p2) -> Integer.compare(p1.getPriority(), p2.getPriority())); // Mengurutkan antrian
                                                                                            // berdasarkan prioritas

        System.out.println("Patient added successfully."); // Pesan konfirmasi pasien ditambahkan
    }

    private static void serveNextPatient() {
        System.out.println("\n--- Layanan pasien berikutnya ---"); // Pesan melayani pasien
        if (patientQueue.isEmpty()) { // Mengecek apakah antrian kosong
            System.out.println("Tidak ada pasien  dalam antrian."); // Menampilkan pesan jika antrian kosong
            return;
        }
        Patient nextPatient = patientQueue.remove(0); // Mengambil pasien pertama (dengan prioritas tertinggi)
        System.out.println("Melayani Pasien: " + nextPatient.getName() + " (Prioritas: "
                + getPriorityText(nextPatient.getPriority()) + ")"); // Menampilkan informasi pasien yang dilayani
    }

    private static void displayQueue() {
        System.out.println("\n--- Antrian pasien saat ini ---"); // Pesan untuk menampilkan antrian
        if (patientQueue.isEmpty()) { // Mengecek apakah antrian kosong
            System.out.println("Antriannya kosong."); // Menampilkan pesan jika antrian kosong
            return;
        }
        // Menampilkan header tabel dengan format tertentu
        System.out.printf("%-20s %-5s %-20s %-15s%n", "Name", "Age", "Condition", "Priority");
        System.out.println("-------------------------------------------------------------");
        for (Patient p : patientQueue) { // Mengiterasi setiap pasien dalam antrian
            // Menampilkan data pasien dalam format tabel
            System.out.printf("%-20s %-5d %-20s %-15s%n", p.getName(), p.getAge(), p.getCondition(),
                    getPriorityText(p.getPriority()));
        }
    }

    private static void updatePriority() {
        System.out.println("\n--- Perbarui prioritas---"); // Pesan untuk memperbarui prioritas
        if (patientQueue.isEmpty()) { // Mengecek apakah antrian kosong
            System.out.println("The queue is empty."); // Menampilkan pesan jika antrian kosong
            return;
        }
        String name = getValidStringInput("Masukkan nama pasien yang ingin dicari: "); // Input nama pasien yang ingin
                                                                                       // diperbarui prioritasnya
        boolean found = false; // Flag untuk mengecek apakah pasien ditemukan
        for (Patient p : patientQueue) { // Mengiterasi setiap pasien dalam antrian
            if (p.getName().equalsIgnoreCase(name)) { // Jika nama pasien ditemukan
                int newPriority = getValidIntInRange("Enter new priority (1-Critical to 5-Non-urgent): ", 1, 5); // Input
                                                                                                                 // prioritas
                                                                                                                 // baru
                p.setPriority(newPriority); // Memperbarui prioritas pasien
                patientQueue.sort((p1, p2) -> Integer.compare(p1.getPriority(), p2.getPriority())); // Mengurutkan ulang
                                                                                                    // antrian
                                                                                                    // berdasarkan
                                                                                                    // prioritas
                System.out.println("Priority updated successfully for patient: " + p.getName()); // Pesan konfirmasi
                found = true; // Menandakan pasien ditemukan
                break;
            }
        }
        if (!found) { // Jika pasien tidak ditemukan
            System.out.println("Pasien tidak ditemukan dalam antrian."); // Pesan jika pasien tidak ditemukan
        }
    }

    private static void searchPatient() {
        System.out.println("\n--- Search Patient ---"); // Pesan untuk mencari pasien
        if (patientQueue.isEmpty()) { // Mengecek apakah antrian kosong
            System.out.println("The queue is empty."); // Menampilkan pesan jika antrian kosong
            return;
        }
        String name = getValidStringInput("Enter the name of the patient to search: "); // Input nama pasien yang dicari
        boolean found = false; // Flag untuk mengecek apakah pasien ditemukan
        for (Patient p : patientQueue) { // Mengiterasi setiap pasien dalam antrian
            if (p.getName().equalsIgnoreCase(name)) { // Jika nama pasien ditemukan
                // Menampilkan informasi pasien
                System.out.println("Patient found:");
                System.out.println("Name: " + p.getName());
                System.out.println("Age: " + p.getAge());
                System.out.println("Condition: " + p.getCondition());
                System.out.println("Priority: " + getPriorityText(p.getPriority()));
                found = true; // Menandakan pasien ditemukan
                break;
            }
        }
        if (!found) { // Jika pasien tidak ditemukan
            System.out.println("Pasien tidak ditemukan dalamantrian."); // Pesan jika pasien tidak ditemukan
        }
    }

    private static String getPriorityText(int priority) {
        // Mengembalikan deskripsi prioritas berdasarkan angka prioritas
        switch (priority) {
            case 1:
                return "1-Critical"; // Prioritas 1 - Kritis
            case 2:
                return "2-Urgent"; // Prioritas 2 - Mendesak
            case 3:
                return "3-High"; // Prioritas 3 - Tinggi
            case 4:
                return "4-Medium"; // Prioritas 4 - Sedang
            case 5:
                return "5-Non-urgent"; // Prioritas 5 - Tidak Mendesak
            default:
                return "Unknown"; // Prioritas tidak valid
        }
    }

    private static int getValidIntInput(String prompt) {
        int value;
        while (true) { // Loop untuk memastikan input valid
            System.out.print(prompt); // Menampilkan prompt
            try {
                value = Integer.parseInt(scanner.nextLine().trim()); // Mencoba untuk mengkonversi input ke integer
                break; // Jika berhasil, keluar dari loop
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number."); // Menampilkan pesan kesalahan jika input
                                                                             // tidak valid
            }
        }
        return value; // Mengembalikan nilai input yang valid
    }

    private static int getValidIntInRange(String prompt, int min, int max) {
        int value;
        while (true) { // Loop untuk memastikan input berada dalam rentang yang valid
            value = getValidIntInput(prompt); // Membaca input integer dari pengguna
            if (value >= min && value <= max) { // Jika input berada dalam rentang yang valid
                break; // Keluar dari loop
            }
            System.out.println("Please enter a value between " + min + " and " + max + "."); // Menampilkan pesan jika
                                                                                             // input di luar rentang
        }
        return value; // Mengembalikan nilai input yang valid
    }

    private static String getValidStringInput(String prompt) {
        String value;
        while (true) { // Loop untuk memastikan input string valid
            System.out.print(prompt); // Menampilkan prompt
            value = scanner.nextLine().trim(); // Membaca input string dari pengguna
            if (!value.isEmpty()) { // Jika input tidak kosong
                break; // Keluar dari loop
            }
            System.out.println("Input cannot be empty. Please try again."); // Pesan jika input kosong
        }
        return value; // Mengembalikan input string yang valid
    }
}

// Kelas untuk mendeskripsikan pasien
class Patient {
    private String name; // Nama pasien
    private int age; // Umur pasien
    private String condition; // Kondisi medis pasien
    private int priority; // Prioritas pasien (1 - kritis, 5 - tidak mendesak)

    public Patient(String name, int age, String condition, int priority) {
        this.name = name; // Inisialisasi nama pasien
        this.age = age; // Inisialisasi umur pasien
        this.condition = condition; // Inisialisasi kondisi medis pasien
        this.priority = priority; // Inisialisasi prioritas pasien
    }

    // Getter dan Setter untuk properti pasien
    public String getName() {
        return name; // Mengembalikan nama pasien
    }

    public int getAge() {
        return age; // Mengembalikan umur pasien
    }

    public String getCondition() {
        return condition; // Mengembalikan kondisi medis pasien
    }

    public int getPriority() {
        return priority; // Mengembalikan prioritas pasien
    }

    public void setPriority(int priority) {
        this.priority = priority; // Mengatur prioritas pasien
    }
}

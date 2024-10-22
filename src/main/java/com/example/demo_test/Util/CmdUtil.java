package com.example.demo_test.Util;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.management.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.management.OperatingSystemMXBean;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CmdUtil {
    public static void executeRunScript(String module, String selection) {
        try {
            String workingDirectory = "/u01/tsdc-backend/" + module;

            ProcessBuilder processBuilder = new ProcessBuilder("./run.sh", selection);

            processBuilder.directory(new File(workingDirectory));

            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            if (exitCode != 0){
                log.error("Loi");
            }
        } catch (Exception e) {
            log.error("Lỗi trong quá trình thực thi");
        }
    }

    // Hàm để lấy thông tin chi tiết cho từng PID
//    public static void getProcessDetails(String pid) {
//        try {
//            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "ps -p " + pid + " -o %cpu,%mem,etime");
//            Process process = processBuilder.start();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println("Thông tin CPU, Memory và thời gian chạy của PID " + pid + ": " + line);
//            }
//
//            int exitCode = process.waitFor();
//            if (exitCode == 0) {
//                System.out.println("Lấy thông tin chi tiết của PID " + pid + " thành công.");
//            } else {
//                System.out.println("Lỗi khi lấy thông tin chi tiết của PID " + pid);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    //
    //
    public static void getInfoV3(){
        Runtime runtime = Runtime.getRuntime();
        long div = 1024 * 1024;
        log.info("Max memory : {} MB \t Free memory : {} MB\t Total memory : {} MB", runtime.maxMemory() / div,
                runtime.freeMemory() / div, runtime.totalMemory() / div);

        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();


        // Lấy thông tin CPU của tiến trình Java hiện tại
        double processCpuLoad = osBean.getProcessCpuLoad() * 100;
        log.info("Process CpuLoad : {} %", processCpuLoad);


        long processCpuTime = osBean.getProcessCpuTime();
        log.info("Process cpu time : {} ms", processCpuTime / 1000000);
        // Lấy thông tin của RuntimeMXBean
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

        // Lấy tên của tiến trình (thường bao gồm PID@hostname)
        String name = runtimeMXBean.getName();

        // Tách PID từ chuỗi name
        String pid = name.split("@")[0];

        log.info("PID: {} ", pid);
        //
        log.info("Operating System: {} {}", osBean.getName(), osBean.getVersion());
        log.info("OS Architecture: {}", osBean.getArch());
        log.info("Available Processors (cores): {}", osBean.getAvailableProcessors());
        //
        long uptime = runtimeMXBean.getUptime();
        log.info("JVM Uptime: {} ms ({} hours)", uptime, uptime / (1000 * 60 * 60));
        //
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        int threadCount = threadMXBean.getThreadCount();
        int daemonThreadCount = threadMXBean.getDaemonThreadCount();
        long totalStartedThreadCount = threadMXBean.getTotalStartedThreadCount();
        int peakThreadCount = threadMXBean.getPeakThreadCount();

        log.info("Current thread count: {}", threadCount);
        log.info("Daemon thread count: {}", daemonThreadCount);
        log.info("Total started thread count: {}", totalStartedThreadCount);
        log.info("Peak thread count: {}", peakThreadCount);

    }
    //
    public static void init(){
        long start = System.currentTimeMillis();
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        ArrayList<Integer> list3 = new ArrayList<>();
        Random rand  = new Random();
        int n = 10000000;
        for (int i = 0; i < n; i++){
            list1.add(rand.nextInt(n));
            list3.add(rand.nextInt(n));
            list2.add(rand.nextInt(n));
        }


        long end = System.currentTimeMillis();
        System.out.println("Thời gian chạy: " + (end - start) + " ms");
    }
    public static void init2(){
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        ArrayList<Integer> list2 = new ArrayList<Integer>();
        ArrayList<Integer> list3 = new ArrayList<Integer>();
        Random rand  = new Random();
        int n = 10000000;
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        futures.add(CompletableFuture.runAsync(() -> {
            for (int i = 0; i < n; i++) {
                list1.add(rand.nextInt(n));
            }
        }, executorService));
        futures.add(CompletableFuture.runAsync(() -> {
            for (int i = 0; i < n; i++) {
                list2.add(rand.nextInt(n));
            }
        }, executorService));
        futures.add(CompletableFuture.runAsync(() -> {
            for (int i = 0; i < n; i++) {
                list3.add(rand.nextInt(n));
            }
        }, executorService));
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("Multi-Thread:" + (end - start));
    }
    public static String getPublicIP() throws Exception {
        // URL của dịch vụ trả về địa chỉ IP public
        String url = "https://api.ipify.org"; // Hoặc có thể sử dụng: "http://checkip.amazonaws.com/"

        // Tạo một kết nối HTTP tới dịch vụ đó
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        // Thiết lập phương thức GET để lấy dữ liệu
        connection.setRequestMethod("GET");

        // Đọc phản hồi từ dịch vụ
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Địa chỉ IP public sẽ nằm trong phản hồi của dịch vụ
        return response.toString();
    }
    public static void main(String[] args) throws Exception {
        try{
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println(localHost);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
//        String publicIP = getPublicIP();
//        System.out.println(publicIP);
//        init();
//        init2();
        getInfoV3();
//        getInfor();
//        System.out.println("LONGLONG");
//        printThreadDetails();
//        System.out.println("LONGLONG");
//        printMemoryInfo();
//        System.out.println("LONGLONG");
//        try {
//            // Sử dụng ProcessBuilder để thực thi lệnh hệ thống Windows
//            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "tasklist /fo csv /nh");
//
//            Process process = processBuilder.start();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            System.out.println("Process Name, PID, Session Name, Session#, Mem Usage");
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}

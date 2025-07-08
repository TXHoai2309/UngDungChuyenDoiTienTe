package com.example.ungdungchuyendoitiente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class AboutUsDialog {
    public static void show(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_about_us, null);
        builder.setView(dialogView);

        // Ánh xạ các TextView trong dialog
        TextView tvAbousUs = dialogView.findViewById(R.id.tvAbouUs);
        Button btnClose = dialogView.findViewById(R.id.btnCloseDialog);

        tvAbousUs.setText(getAboutUsText());

        AlertDialog dialog = builder.create();
        dialog.show();

        btnClose.setOnClickListener(v -> dialog.dismiss());
    }

    private static String getAboutUsText() {
        return "1. Về ứng dụng / About the App\n" +
                "\n" +
                "- Ứng dụng [Ứng dụng chuyển đổi tiền tệ] là một nền tảng chuyển đổi tiền tệ thông minh, được thiết kế nhằm hỗ trợ người dùng dễ dàng theo dõi và chuyển đổi tỷ giá ngoại tệ trên toàn cầu. Với công nghệ hiện đại và kết nối trực tiếp với các API tài chính đáng tin cậy (như Alpha Vantage, ExchangeRate API...), ứng dụng mang đến tỷ giá cập nhật theo thời gian thực, giúp bạn ra quyết định nhanh chóng và chính xác.\n" +
                "\n" +
                "- Giao diện của ứng dụng được tối ưu hóa để phù hợp với mọi đối tượng người dùng: từ sinh viên, người đi du lịch, nhà đầu tư cá nhân đến các chuyên gia tài chính. Tính năng hỗ trợ lưu lịch sử chuyển đổi, tùy chỉnh đơn vị tiền tệ, và làm việc offline (khi không có mạng) giúp nâng cao trải nghiệm người dùng.\n" +
                "\n" +
                "- Currency Converter App] is a smart currency conversion platform designed to help users easily monitor and convert exchange rates worldwide. Powered by modern technology and real-time connections to trusted financial APIs (such as Alpha Vantage, ExchangeRate API...), the app provides up-to-date exchange rates to support fast and accurate decision-making.\n" +
                "\n" +
                "- Its interface is optimized for all types of users – students, travelers, individual investors, and financial professionals. Features like historical conversion storage, customizable currency units, and offline mode (with cached data) enhance overall user experience.\n" +
                "\n" +
                "2. Sứ mệnh và tầm nhìn / Mission and Vision\n" +
                "\n" +
                "- Chúng tôi tin rằng tài chính cá nhân bắt đầu từ sự hiểu biết về tiền tệ. Sứ mệnh của chúng tôi là mang đến một công cụ đơn giản, dễ dùng nhưng mạnh mẽ, giúp người dùng quản lý tài chính cá nhân tốt hơn trong thời đại số.\n" +
                "\n" +
                "- Tầm nhìn của chúng tôi là xây dựng một hệ sinh thái ứng dụng tài chính đa năng, trong đó [Tên Ứng Dụng] sẽ là một trong những ứng dụng chuyển đổi tiền tệ được tin dùng nhất tại Việt Nam và vươn ra thị trường quốc tế.\n" +
                "\n" +
                "W- e believe that personal finance starts with understanding currency. Our mission is to provide a simple yet powerful tool that helps users manage their finances better in the digital age.\n" +
                "\n" +
                "- Our vision is to build a versatile financial app ecosystem, in which [App Name] will be one of the most trusted currency converter applications in Vietnam and beyond.\n" +
                "\n" +
                "3. Tính năng chính / Key Features\n" +
                "\n" +
                "-  Chuyển đổi tiền tệ thời gian thực từ hơn 170 quốc gia\n" +
                "\n" +
                "-  Tính toán số tiền nhanh chóng với bàn phím tích hợp\n" +
                "\n" +
                "- Hiển thị biểu đồ tỷ giá (có thể mở rộng trong tương lai)\n" +
                "\n" +
                "- Lưu lịch sử chuyển đổi\n" +
                "\n" +
                "- Làm việc offline với dữ liệu đã lưu\n" +
                "\n" +
                "- Hỗ trợ chế độ sáng – tối (Dark/Light Mode)\n" +
                "\n" +
                "- Giao diện song ngữ Việt – Anh (hoặc tự động theo ngôn ngữ máy)\n" +
                "\n" +
                "4. Thông tin nhà phát triển / Developer Info\n" +
                "\n" +
                "Nhà phát triển / Developer: Nhóm 3\n" +
                "\n" +
                "Email hỗ trợ: support@example.com\n" +
                "\n" +
                "Phiên bản ứng dụng / App Version: 1.0.0\n" +
                "\n" +
                "Địa chỉ văn phòng: Hà Nội, Việt Nam\n" +
                "\n" +
                "Website: www.example.com (tuỳ chỉnh nếu có)\n" +
                "\n" +
                "❤\uFE0F Cảm ơn bạn đã tin tưởng và sử dụng ứng dụng của chúng tôi!\n" +
                "Chúng tôi luôn mong nhận được ý kiến đóng góp từ bạn để ngày càng hoàn thiện hơn.\n" +
                "\n" +
                "Thank you for trusting and using our app!\n" +
                "We always welcome your feedback to help us improve continuously.";
    }
}

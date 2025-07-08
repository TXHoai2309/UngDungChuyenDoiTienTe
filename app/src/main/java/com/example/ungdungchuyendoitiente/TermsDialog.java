package com.example.ungdungchuyendoitiente;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TermsDialog {

    public static void show(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_terms_conditions, null);
        builder.setView(dialogView);

        TextView tvTerms = dialogView.findViewById(R.id.tvTermsDialog);
        Button btnClose = dialogView.findViewById(R.id.btnCloseDialog);

        tvTerms.setText(getTermsText());

        AlertDialog dialog = builder.create();
        dialog.show();

        btnClose.setOnClickListener(v -> dialog.dismiss());
    }

    private static String getTermsText() {
        return "📄 ĐIỀU KHOẢN VÀ ĐIỀU KIỆN SỬ DỤNG / TERMS AND CONDITIONS OF USE\n\n"
                + "1. Giới thiệu / Introduction\n"
                + "- Bằng việc sử dụng ứng dụng [Tên Ứng Dụng], bạn đồng ý với các điều khoản này.\n"
                + "- By using the app, you agree to the following terms.\n\n"

                + "2. Tài khoản người dùng / User Account\n"
                + "- Cần đăng nhập để sử dụng các chức năng nâng cao.\n"
                + "- Người dùng chịu trách nhiệm bảo mật thông tin.\n"
                + "- Login is required for advanced features.\n"
                + "- Users are responsible for keeping their credentials secure.\n\n"

                + "3. Nguồn dữ liệu & API / Data Sources & APIs\n"
                + "- Ứng dụng sử dụng API bên thứ ba để lấy tỷ giá.\n"
                + "- Không chịu trách nhiệm nếu dữ liệu bị sai hoặc trễ.\n"
                + "- The app fetches exchange rates from third-party APIs.\n"
                + "- We are not liable for inaccurate or delayed data.\n\n"

                + "4. Tính chính xác / Accuracy\n"
                + "- Tỷ giá chỉ mang tính tham khảo.\n"
                + "- Không sử dụng để giao dịch chính thức.\n"
                + "- Exchange rates are for reference only.\n"
                + "- Do not use for real financial transactions.\n\n"

                + "5. Quyền sở hữu trí tuệ / Intellectual Property\n"
                + "- Mọi nội dung thuộc về nhà phát triển.\n"
                + "- Cấm sao chép hoặc sử dụng trái phép.\n"
                + "- All content belongs to the developer.\n"
                + "- Unauthorized use or reproduction is prohibited.\n\n"

                + "6. Bảo mật / Privacy\n"
                + "- Chúng tôi cam kết bảo vệ thông tin cá nhân.\n"
                + "- Không chia sẻ cho bên thứ ba nếu không có sự đồng ý.\n"
                + "- We are committed to protecting your privacy.\n"
                + "- No personal data is shared without your consent.\n\n"

                + "7. Giới hạn trách nhiệm / Limitation of Liability\n"
                + "- Người dùng chịu trách nhiệm với mọi hành vi sử dụng.\n"
                + "- Chúng tôi không chịu trách nhiệm với thiệt hại phát sinh.\n"
                + "- You use this app at your own risk.\n"
                + "- We are not liable for any resulting damages.\n\n"

                + "8. Thay đổi điều khoản / Modifications\n"
                + "- Có thể cập nhật điều khoản bất cứ lúc nào.\n"
                + "- Tiếp tục sử dụng đồng nghĩa bạn đồng ý với điều khoản mới.\n"
                + "- Terms may be updated at any time.\n"
                + "- Continued use implies acceptance of changes.\n\n"

                + "9. Liên hệ / Contact\n"
                + "Email: ungdungchuyendoitiente.contact@gmail.com\n"
                + "SĐT / Phone: 0123 456 789\n\n"

                + "✅ Bằng việc sử dụng ứng dụng, bạn xác nhận đã đọc và đồng ý với các điều khoản này.\n"
                + "✅ By using this app, you confirm that you have read and accepted these terms.";
    }
}

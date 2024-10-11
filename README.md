htruong48 - 9/10/24
# Trong này chứa các tool sau: 
# 1 - code convert data từ mongo db thành JSON chuẩn
# 2 - Có code compare JSON dựa theo field. Tạo map <key,value> = <field, field_value> để kiểm tra 
# 3 - Convert all file csv --> file excel
# 4 - Compare Excel --> thống kê đưa ra file mới
# 5 - Auto gen lệnh sql để cập nhật thông tin dựa trên dữ liệu khác nhau trong file excel
-- 11/10: Nâng cấp tool = multi thread, mình sử dụng 4 luồng để thực hiện đọc dữ liệu, sau đó tiếp tục chia nhỏ để có kết quả đánh giá nhanh nhất ghi vào các sheet, sau đó gen sql cập nhật db

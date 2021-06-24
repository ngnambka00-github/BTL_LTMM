# Hệ mật RC4

- Bài tập lớn môn Lý Thuyết Mật Mã - Kỳ 20202
- Công cụ sử dụng: 
	+ Backend API: Java Framework Spring Boot
	+ Frontend: HTML, CSS, Javascript
	+ Phần mềm sử dụng: IntelliJ IDEA JetBrains 2021.1.2 (Phiên bản Java sử dụng cho Project là JDK 8 hoặc JDK 11 đểu thỏa mãn là sử dụng ổn định)
		+ Đường dẫn phần mềm: https://www.jetbrains.com/idea/download/#section=windows
		+ Đường dẫn phiên bản JDK 8: https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
		+ Đường dẫn phiên bản JDK 11: https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
		+ Do server Tomcat đã được tích hợp sắn trong framework Spring Boot, nên không cần cài đặt thêm server khác.
- Cách sử dụng: 
	+ B1: Download Project từ đường dẫn https://github.com/ngnambka00-github/BTL_LTMM 
		
		Hoặc tạo folder trống trên máy với công cụ Git Command và gõ lệnh sau: git clone https://github.com/ngnambka00-github/BTL_LTMM.git
	+ B2: Mở phần mềm bằng công cụ IntelliJ (như ở phần trên đã trình bày)
	+ B3: Chạy "Run" trên phần mềm
	+ B4: Mở trình duyệt theo đường dẫn: http://localhost:8091/BTL_LTMM 
		(Trên là port mặc định local của Project, nếu port 8091 đã được một tiến trình hoặc phần mềm khác sử dụng thì ta tiến hành đổi port khác)
	=> Cách đối port khác: 
		+ Từ đường dẫn gốc Project tìm đếm : ../BTL_LTMM/src/main/resources/application.properties
		+ Thay đổi bằng lệnh: server.port= <port mới>
		+ Tiến hành mở lại trên trình duyệt bằng URL: "http://localhost:<port mới>/BTL_LTMM"



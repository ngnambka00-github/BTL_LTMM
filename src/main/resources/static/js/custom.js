// Các biến globel chạy toàn bộ chương trình
let contextPath = 'BTL_LTMM';
let urlDefault = `${window.location.origin}/${contextPath}`;

$(document).ready(function() {
    // Lưu file image vào resources/static của project
    $("input[type='file']").on('change', function(event){
        // Lấy danh sách các file được chọn
        let files = event.target.files;

        // Truyền tập các file được chọn vào hàm để thực hiện upload
        let check = uploadFileToBackend(
            `${urlDefault}/api/uploadfile`,
            files
        );
        let imgPlain = $('#plain-img');
        let imgCipher = $('#cipher-img');
        if ($('#id-encryption').is(':checked')) {
            // Hiển thị ảnh vừa upload lên giao diện
            if (check) {
                updateImage(imgPlain,
                    `${urlDefault}/api/getplains/${files[0].name}`,
                    `${files[0].name}`);
            }
            // Nếu check = false => tức upload lỗi => xuất ra ảnh lỗi
            else {
                updateImage(imgPlain,
                    `${urlDefault}/api/getplains/error_img.jpg`,
                    `error-img.png`);
            }
            clearImage(imgCipher);
        } else {
            // Hiển thị ảnh vừa upload lên giao diện
            if (check) {
                updateImage(imgCipher,
                    `${urlDefault}/api/getplains/${files[0].name}`,
                    `${files[0].name}`);
            }
            // Nếu check = false => tức upload lỗi => xuất ra ảnh lỗi
            else {
                updateImage(imgCipher,
                    `${urlDefault}/api/getplains/error_img.jpg`,
                    `error-img.png`);
            }
            clearImage(imgPlain);
        }

    });

    $('#btn-execute').on('click', function() {
        let keyStr = $('#id-key').val();
        if (keyStr === '') {
            alert("Key đang rỗng");
            return;
        }
        let fileName = '';

        let imgPlain = $('#plain-img');
        let imgCipher = $('#cipher-img');
        if ($('#id-encryption').is(':checked')) {
            fileName = imgPlain.attr('data-name-image');
            updateImage(imgCipher,
                `${urlDefault}/api/getciphers/${fileName}/${keyStr}`,
                `${fileName}`);
        } else {
            fileName = imgCipher.attr('data-name-image');
            updateImage(imgPlain,
                `${urlDefault}/api/getciphers/${fileName}/${keyStr}`,
                `${fileName}`);
        }
    });
});

// Call API to upload file image to server
function uploadFileToBackend(url, files) {
    let result = '';
    // Tạo đối tượng fỏm để thực hiện post dữ liệu lên
    let forms = new FormData();
    forms.append('file', files[0]);

    $.ajax({
        url: url,
        type: 'POST', // vì dữ liệu lớn => cần sử dụng phương thức POST
        data: forms,
        async: false,
        // không được định dạng thành 1 kiểu j -> vì đã được mà hóa
        contentType: false,
        // Không được thực hiện xử lý form
        processData: false,
        enctype: 'multipart/form-data',
        success: function(value) {
            result = value;
        },
        error: function(error) {
            // Xử lý lỗi
        }
    });
    return result !== ''
}

function updateImage(tagName, url, fileImageName) {
    tagName.attr(
        {
            'src': url,
            'data-name-image': fileImageName
        }
    );
}

function clearImage(tagName) {
    tagName.attr(
        {
            'src': ''
        }
    );
}
$(document).ready(() => {
    $('#summernote').summernote({
        placeholder: 'Hello Bootstrap 4',
        tabsize: 4,
        height: 300
    });

    $('#del_file_btn').on('click', () => {
        deleteFile();
    });

    function deleteFile() {
        let del_fileArea = document.getElementById('del_file_area');
        let parentNode = del_fileArea.parentNode;
        parentNode.removeChild(del_fileArea);
    }
});
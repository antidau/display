$(function(){

    function loadSceneList() {
        $().ajax({
            page: '/json/scenes'
        })
    }
    
    $('#table2').colResizable();
});
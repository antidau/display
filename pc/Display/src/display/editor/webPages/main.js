$(function(){

    function loadSceneList() {
        $.ajax({
            url: '/json/scene/list',
            dataType: 'json',
            success: function(msg) {
                alert(msg[1]);
            },
            fail: function(err) {
                alert(err);
            }
            
        })
    }
    loadSceneList();
});
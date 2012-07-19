$(function(){
        
    sceneList = $('#sceneList');
    loading = "loading";

    function loadSceneList() {
        sceneList.html(loading);
        $.ajax({
            url: '/json/scene/list',
            dataType: 'json',
            success: function(msg) {
                sceneList.html('');
                for (i=0;i<msg.length;i++) {
                    alert(msg[i]);
                    sceneList.append('<div class="scene">'+msg[i]+'</div>');
                }
            },
            fail: function(err) {
                alert(err);
            }
            
        })
    }
    loadSceneList();
});
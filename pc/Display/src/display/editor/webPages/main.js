$(function(){
        
    sceneList = $('#sceneList');
    details = $('#details');
    loading = "loading";

    function loadSceneList() {
        sceneList.html(loading);
        $.ajax({
            url: '/json/scene/list',
            dataType: 'json',
            success: function(msg) {
                sceneList.html('');
                for (i=0;i<msg.length;i++) {
                    sceneList.append('<a href="javascript:loadProperties('+i+');">'+msg[i]+'</div>');
                }
            },
            fail: function(err) {
                alert(err);
            }
            
        })
    }
    loadSceneList();
    
    loadProperties = function(id) {
        details.html(loading);
        $.ajax({
            url: '/json/scene/properties?id='+id,
            dataType: 'json',
            success: function(msg) {
                
                lines = msg.reduce(function (l,prop) {
                    return l+"<tr><td>"+prop.name+"</td><td>"+prop.content+"</td></tr>";
                },'');
                details.html("<table><tr><th>Name</th><th>Content</th></tr>"+lines+"</table>");
                
            },
            fail: function(err) {
                alert(err);
            }
        });
    }
    
    add = function(type) {
        
        if (type) {
            $.ajax({
                url: '/json/scene/add?name='+type,
                dataType: 'json',
                success: function(msg) {

                    if (msg) loadSceneList();
                    else alert('Adding '+type+' failed.');
                },
                fail: function(err) {
                    alert(err);
                }
            });
        } else {
            
            details.html(loading);
            $.ajax({
                url: '/json/types',
                dataType: 'json',
                success: function(msg) {

                    lines = msg.reduce(function (l,type) {
                        return l+'<button onclick="add(\''+type+'\')">Add '+type+'</button>';
                    },'');
                    details.html(lines);

                },
                fail: function(err) {
                    alert(err);
                }
            });
            
        }
        
    }
});
var loadProperties;
var add;


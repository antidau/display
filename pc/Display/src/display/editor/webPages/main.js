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
            error: function(err) {
                alert(err);
            }
            
        })
    }
    loadSceneList();
    
    loadProperties = function(id) {
        currentId = id;
        details.html(loading);
        $.ajax({
            url: '/json/scene/properties?id='+id,
            dataType: 'json',
            success: function(msg) {
                properties = msg;
                lines = msg.reduce(function (l,prop) {
                    if (prop.name=="name") return l;
                    
                    var cont;
                    if (prop.writable) {
                        var editor = editors[prop.type];
                        if (editor===undefined) {
                            editor = editors['_default'];
                        }
                        if (editor)
                            cont = editor(prop.name,prop.content,prop.type);
                        else cont="No default editor";
                    } else cont = prop.content;
                    
                    
                    return l+"<tr><td>"+prop.name+"</td><td>"+cont+"</td><td>"+prop.type+"</td><td>"+prop.writable+"</td></tr>";
                },'');
                details.html("<table><tr><th>Name</th><th>Content</th><th>Type</th><th>Writable</th></tr>"+lines+"</table>"+
                
                    '<button onclick="save()">Save changes</button><button onclick="deleteScene()">Delete this scene</button>');
                
            },
            error: function(err) {
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
                error: function(err) {
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
                error: function(err) {
                    alert(err);
                }
            });
            
        }
        
    }
    
    
    save = function() {
        var saveData={};
        properties.forEach(function(prop) {
            if (!prop.writable) return;
           
            var edit = $('#prop-'+prop.name).val();
            saveData['prop-'+prop.name] = edit;
        });
        saveData.id = currentId;
        console.log(saveData);
            $.ajax({
                url: '/json/scene/saveProps',
                //dataType: 'json',
                data: saveData,
                success: function(msg) {

                    alert(msg);

                },
                error: function(err) {
                    alert(err);
                }
            });
    }
    
    deleteScene = function() {
        alert("deleting "+currentId);
    }
    
    
    
    function createSelect(options) {
        
        return function(name, content,type) {
            
            
            return '<select name="prop-'+name+'" id="prop-'+name+'">'+
            options.reduce(function(l,item){
                var sel = "";
                if (content==item.value) sel = " selected";
                return l+'<option value="'+item.value+'"'+sel+'>'+item.display+'</option>';
                
            },'')+'</select>';
        }
        
    }
    
    function createText(onlyNumbers,allowPoint) {
       
        return function(name,content,type) {
            if (onlyNumbers) {
                setTimeout(function(){
                    $('#prop-'+name).forceNumericOnly(allowPoint);
                },10);
            }
            return '<input type="text" id="prop-'+name+'" name="prop-'+name+'" value="'+content.replace(/"/g,"&quot;")+'" />';
        } 
        
        
    }
    
    //Editors is an array with the variable type as key and a function to generate
    //an editing field as key.
    //The function gets these parameters: name, content, type
    //And should return html code to display the editor
    //It should take care that the user input is available as $('#prop-<name>')
    //where <name> is the first parameter.
    var editors = [];
    editors['String'] = createText(false);
    editors['Character'] = editors['String'];
    
    editors['boolean'] = createSelect([
    {
        value:'true',
        display:'True'
    },
    {
        value:'false',
        display:'False'
    }
    ]);
    
    editors['SoundSceneStyle'] = createSelect([
    {
        value:'BAR',
        display:'Bar'
    },
    {
        value:'COLOR',
        display:'Color'
    }
    ]);
    
    editors['int'] = createText(true,false);
    editors['float'] = createText(true,true);
    
    editors['_default'] = function(name,content,type) {
        return "no editor for "+ type+" with name "+name+" and content "+content;
    }
    
    jQuery.fn.forceNumericOnly =
    function(allowPoint)
    {
        return this.each(function()
        {
            $(this).keydown(function(e)
            {
                var key = e.charCode || e.keyCode || 0;
                // allow backspace, tab, delete, arrows, numbers, F1-F12 and keypad numbers ONLY
                console.log(key);
                return e.ctrlKey||(
                    key == 8 || 
                    key == 9 ||
                    key == 46 ||
                    key == 109 ||
                    key == 173 ||
                    (((key >= 37 && key <= 40) ||
                        (key >= 48 && key <= 57) ||
                        (key >= 96 && key <= 105))&& !e.shiftKey)
                    || ((key==190) && allowPoint)
                    || (key >= 112 && key <= 123));
            });
        });
    };
});
//Functions declared in jQuery callback but need global visibility
var loadProperties;
var add;
var save;
var deleteScene;

//Variables
var currentId;
var properties;


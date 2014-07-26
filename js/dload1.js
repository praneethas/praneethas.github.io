$(function() {
        var baseURL = 'tabs/bookssem/';
        console.log("Hello World" + baseURL);
        //load content for first tab and initialize
        $('#GATE').load(baseURL+'gate.html', function() {
          console.log("Home executes");
            $('#semTabs').tab(); //initialize tabs
        });    
        $('#semTabs li a').bind("click", function(e) {    
            var contentID  = $(e.target).attr("data-target");
            var contentURL = $(e.target).attr("href");
            console.log("Func executes");
            console.log("contentID: " + contentID);
            console.log("contentURL: " + contentURL);
            //load content for selected tab
            if (typeof(contentURL) != 'undefined')
              $(contentID).load(baseURL+contentURL, function(){
                  $('#semTabs').tab(); //reinitialize tabs
              });
            else
              $(contentID).tab('show');
        });
        //$('#semTabs a:[data-target="#cd"]').tab("show");
});
//console.log("Hello World");
/*$(document).ready(function(){
  var baseURL = 'tabs/bookssem/';
  $('#s1').load(baseURL+'sem1.html');
  console.log("Sem 1 Loaded");
})
console.log("Sem Loaded");
$(function() {
  $("#semTabs").tab();
  console.log("Sem 2 Loaded");
  $("#semTabs").bind("show", function(e) {    
    console.log("Sem 3 Loaded");
    var contentID  = $(e.target).attr("data-target");
    var contentURL = $(e.target).attr("href");
    if (typeof(contentURL) != 'undefined')
      $(contentID).load(baseURL+contentURL, function(){ $("#semTabs").tab(); });
    else
      $(contentID).tab('show');
  });
  $('#semTabs a:[data-target="#s1"]').tab("show");
});*/
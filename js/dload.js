$(function() {
        var baseURL = 'tabs/';
        //load content for first tab and initialize
        $('#about').load(baseURL+'about.html', function() {
          console.log("Home executes");
            $('#MainTabs').tab(); //initialize tabs
        });    
        $('#MainTabs li a').bind("click", function(e) {    
            var contentID  = $(e.target).attr("data-target");
            var contentURL = $(e.target).attr("href");
            console.log("Func executes");
            console.log("contentID: " + contentID);
            console.log("contentURL: " + contentURL);
            //load content for selected tab
            if (typeof(contentURL) != 'undefined')
              $(contentID).load(baseURL+contentURL, function(){
                  $('#MainTabs').tab(); //reinitialize tabs
              });
            else
              $(contentID).tab('show');
        });
        /*$('#MainTabs a:[data-target="#cd"]').tab("show");*/
});
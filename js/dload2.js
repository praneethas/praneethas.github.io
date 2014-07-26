$(function() {
        var baseURL = 'tabs/prog/';
        console.log("Hello World" + baseURL);
        //load content for first tab and initialize
        $('#c').load(baseURL+'c.html', function() {
          console.log("Home executes");
            $('#progTabs').tab(); //initialize tabs
        });    
        $('#progTabs li a').bind("click", function(e) {    
            var contentID  = $(e.target).attr("data-target");
            var contentURL = $(e.target).attr("href");
            console.log("Func executes");
            console.log("contentID: " + contentID);
            console.log("contentURL: " + contentURL);
            //load content for selected tab
            if (typeof(contentURL) != 'undefined')
              $(contentID).load(baseURL+contentURL, function(){
                  $('#progTabs').tab(); //reinitialize tabs
              });
            else
              $(contentID).tab('show');
        });
        //$('#progTabs a:[data-target="#cd"]').tab("show");
});

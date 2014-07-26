        var pageHeightT = $(window).height();
        var pageWidthT = $(window).width();
        var wellHeightT = $('div.well').css("max-height");
        var wellWidthT = $('div.well').css("max-width");
        /*var wellHeightT = $('div.well').height();
        var wellWidthT = $('div.well').width();*/
        var navHeightT = 0.7*pageHeightT;
        var navWidthT = 0.72*pageWidthT;
        /*var styles = {
            height : "$(navHeightT) px"
        };*/
        console.log("Calling wellProg.js ..... ");
        console.log("Pageheight: " + pageHeightT + " navHeightT: " + navHeightT + " wellHeight: " + wellHeightT);
        console.log("Pagewidth: " + pageWidthT + " navWidthT: " + navWidthT + " wellWidth: " + wellWidthT);
        var color = $('div.well').css( "background-color" );
        console.log("Color: " + color);
        /*$('div.well').css("height: navHeightT + 'px'; background-color: black;");*/
        $( document ).ready(function() {
            //if(wellHeightT > pageHeightT){
                $("div.tab-pane").css({"overflow-y": "scroll"});
                $("div.well").css({"overflow-y": "hidden"});
                $("div.tab-pane").css('max-height', navHeightT+'px');
                var wellHeightT1 = $('div.well').css("max-height");
                var hScrollT = $('div.well').css( "overflow-y" );
                console.log("if1::Pageheight: " + pageHeightT + " navHeightT: " + navHeightT + " wellHeight1: " + wellHeightT1 + " hScroll: " + hScrollT);
            //}
            //if(wellWidthT < pageWidthT){
                //$("div.well").css({"overflow-x": "scroll"});
                $("div.tab-pane").css('max-width', navWidthT+'px');
                var wellWidthT1 = $('div.well').css("max-width");
                console.log("if2::Pagewidth: " + pageWidthT + " navWidthT: " + navWidthT + " wellWidth1: " + wellWidthT1);
            //}
        });
        $( window ).bind("resize", function(){
            // Change the width of the div
            console.log("Calling resize from wellProg.js ....");
            var pageHeightTN = $(window).height();
            var pageWidthTN = $(window).width();
            var wellHeightTN = $('div.well').css("max-height");
            var wellWidthTN = $('div.well').css("max-width");
            var navHeightTN = 0.7*pageHeightTN;
            var navWidthTN = 0.72*pageWidthTN;
            /*$("div.well").width( navWidthTN );
            $("div.well").height( navHeightTN );*/
            if(wellHeightTN > pageHeightTN){
                $("div.tab-pane").css({"overflow-y": "scroll"});
                $("div.well").css({"overflow-y": "hidden"});
                $("div.tab-pane").css('max-height', navHeightTN+'px');
            }
            if(wellWidthTN < pageWidthTN){
                $("div.tab-pane").css('max-width', navWidthTN+'px');
            }
            var wellHeightTN1 = $('div.well').outerHeight();
            var wellWidthTN1 = $('div.well').outerWidth();
            var hScrollTN1 = $('div.well').css( "overflow-y" );
            console.log("PageheightN: " + pageHeightTN + " navHeightTN: " + navHeightTN + " wellHeightN: " + wellHeightTN1 + " wellHeightN1: " + wellHeightTN1 + " hScrollN: " + hScrollTN1);
            console.log("PagewidthN: " + pageWidthTN + " navWidthTN: " + navWidthTN + " wellWidthN: " + wellWidthTN1 + " wellWidthN1: " + wellWidthTN1);
        });
        $('div.well').css(styles);
        var pageHeight = $(window).height();
    	var pageWidth = $(window).width();
    	var wellHeight = $('div.well').outerHeight();
    	var wellWidth = $('div.well').outerWidth();
    	var navHeight = 0.75*pageHeight;
    	var navWidth = 0.7*pageWidth;
        var wellwidth = $('div.well').css("max-width");
        console.log("wellwidth from css: " + wellwidth);
    	/*var styles = {
      		height : "$(navHeight) px"
      	};*/
        console.log("Calling well.js ..... ");
        console.log("Pageheight: " + pageHeight + " navHeight: " + navHeight + " wellHeight: " + wellHeight);
    	console.log("Pagewidth: " + pageWidth + " navWidth: " + navWidth + " wellWidth: " + wellWidth);
    	var color = $('div.well').css( "background-color" );
    	console.log("Color: " + color);
    	/*$('div.well').css("height: navHeight + 'px'; background-color: black;");*/
    	if(wellHeight > pageHeight){
    		$("div.well").css({"overflow-y": "scroll"});
    		$("div.well").css('max-height', navHeight+'px');
    		var wellHeight1 = $('div.well').outerHeight();
            var hScroll = $('div.well').css( "overflow-y" );
    		console.log("Pageheight: " + pageHeight + " navHeight: " + navHeight + " wellHeight1: " + wellHeight1 + " hScroll: " + hScroll);
    	}
    	if(wellWidth < pageWidth){
    		//$("div.well").css({"overflow-x": "scroll"});
    		$("div.well").css('max-width', navWidth+'px');
    		var wellWidth1 = $('div.well').outerWidth();
    		console.log("Pagewidth: " + pageWidth + " navWidth: " + navWidth + " wellWidth1: " + wellWidth1);
    	}
        $( window ).bind("resize", function(){
            // Change the width of the div
            console.log("Calling resize from well.js ....");
            var pageHeightN = $(window).height();
            var pageWidthN = $(window).width();
            var wellHeightN = $('div.well').outerHeight();
            var wellWidthN = $('div.well').outerWidth();
            var navHeightN = 0.75*pageHeightN;
            var navWidthN = 0.7*pageWidthN;
            /*$("div.well").width( navWidthN );
            $("div.well").height( navHeightN );*/
            if(wellHeight > pageHeight){
                $("div.well").css({"overflow-y": "scroll"});
                $("div.well").css('max-height', navHeightN+'px');
            }
            if(wellWidth < pageWidth){
                $("div.well").css('max-width', navWidthN+'px');
            }
            var wellHeightN1 = $('div.well').outerHeight();
            var wellWidthN1 = $('div.well').outerWidth();
            var hScrollN = $('div.well').css( "overflow-y" );
            console.log("PageheightN: " + pageHeightN + " navHeightN: " + navHeightN + " wellHeightN: " + wellHeightN + " wellHeightN1: " + wellHeightN1 + " hScrollN: " + hScrollN);
            console.log("PagewidthN: " + pageWidthN + " navWidthN: " + navWidthN + " wellWidthN: " + wellWidthN + " wellWidthN1: " + wellWidthN1);
        });
    	
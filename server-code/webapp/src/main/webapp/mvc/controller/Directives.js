TaxMachinaApp.directive('tmGoBack', function () {
    return {
        link: function (scope, element, attrs) {
            element.on('click', function () {
                window.history.back();
            });
        }
    };
});


TaxMachinaApp.directive('tmImgCropped', function() {
  return {
    restrict: 'E',
    replace: true,
    scope: { src:'@', selected:'&' },
    link: function(scope,element, attr) {
      var myImg;
      var clear = function() {
        if (myImg) {
          myImg.next().remove();
          myImg.remove();
          myImg = undefined;
        }
      };
      scope.$watch('src', function(nv) {        
        clear();
        if (nv) {
          element.after('<img />');
          myImg = element.next();        
          myImg.attr('src',nv);
          $(myImg).Jcrop({
            trackDocument: true,  
            onSelect: function(x) {              
              scope.$apply(function() {
                scope.selected({cords: x});
              });
            }
          });
        }
      });
      
      scope.$on('$destroy', clear);
    }
  };
});
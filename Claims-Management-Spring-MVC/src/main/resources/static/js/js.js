function checkSupervisorEmail()
{
(async () => {
   let response = await new Promise(resolve => {
      var xhr = new XMLHttpRequest();
      xhr.open("GET",'/supervisors/check/email',true);
      xhr.setRequestHeader('supervisorEmail',document.getElementById('email').value);
      xhr.setRequestHeader('Authorization','Bearer i989saiksjaw98pp;l;l9i89U8U8azAUH');
      xhr.responseType = 'json';
      xhr.onload = function(e) {
          const CheckEmailResponse = xhr.response;
          if(CheckEmailResponse.message.localeCompare("true")==0)
            {      
               document.getElementById('emailExists').style.display="inline";
            }
            else
            {
             document.getElementById('emailExists').style.display="none";
             document.getElementById("createSupervisor").submit();
            }
      }; 
      xhr.send();
   }) 
   doTheThing(response)
})()
}


function changeMaxDate()
{
var d=document.getElementById("dateFin").value;
var dateFin=new Date(d.slice(0,4),d.slice(5,7),d.slice(8,10));
var dayBeforeDateFin=new Date();
dayBeforeDateFin.setDate(dateFin.getDate()-1);
var dd = dayBeforeDateFin.getDate();
var mm = dayBeforeDateFin.getMonth() + 1; 
var yyyy = dayBeforeDateFin.getFullYear();
if (dd < 10) {
  dd = '0' + dd
}
if (mm < 10) {
  mm = '0' + mm
}
dayBeforeDateFin = yyyy + '-' + mm + '-' + dd;
document.getElementById("dateDebut").setAttribute("max",dayBeforeDateFin);
}


var today = new Date();
var beforeOneMonth = new Date();
beforeOneMonth.setDate(today.getDate() - 30);
var before29Days = new Date();
before29Days.setDate(today.getDate() - 29);

var dd = today.getDate();
var mm = today.getMonth() + 1; 
var yyyy = today.getFullYear();
if (dd < 10) {
  dd = '0' + dd
}
if (mm < 10) {
  mm = '0' + mm
}
today = yyyy + '-' + mm + '-' + dd;

var dd = beforeOneMonth.getDate();
var mm = beforeOneMonth.getMonth() + 1; 
var yyyy = beforeOneMonth.getFullYear();
if (dd < 10) {
  dd = '0' + dd
}
if (mm < 10) {
  mm = '0' + mm
}
beforeOneMonth = yyyy + '-' + mm + '-' + dd;

var dd = before29Days.getDate();
var mm = before29Days.getMonth() + 1; 
var yyyy = before29Days.getFullYear();
if (dd < 10) {
  dd = '0' + dd
}
if (mm < 10) {
  mm = '0' + mm
}
before29Days = yyyy + '-' + mm + '-' + dd;

document.getElementById("dateFin").setAttribute("max", today);
document.getElementById("dateFin").setAttribute("min",before29Days);
document.getElementById("dateDebut").setAttribute("min",beforeOneMonth);

function cheakAdminCurrentPassword()
{
(async () => {
   let response = await new Promise(resolve => {
      var xhr = new XMLHttpRequest();
      xhr.open("GET",'/admin/check/password',true);
      xhr.setRequestHeader('currentPassword',document.getElementById('currentPassword').value);
      xhr.setRequestHeader('Authorization','Bearer i989saiksjaw98pp;l;l9i89U8U8azAUH');   
      xhr.responseType = 'json';
      xhr.onload = function() {
        const checkCurrentPassword = xhr.response;
        if(checkCurrentPassword.message.localeCompare("correct")==0)
          {      
             document.getElementById('incorrectPassword').style.display="none";
             var newpassword =document.getElementById('newPassword').value;
             var newPasswordConfirmation =document.getElementById('newPasswordConfirmation').value;
             if(newpassword.localeCompare(newPasswordConfirmation)!=0)
               {
                 document.getElementById('differentPasswords').style.display="inline";
               }
             else
               {
                  document.getElementById('differentPasswords').style.display="none";
                  document.getElementById("updatePwdForm").submit();
               }
          }
          else
          {
             document.getElementById('incorrectPassword').style.display="inline";
             var newpassword =document.getElementById('newPassword').value;
             var newPasswordConfirmation =document.getElementById('newPasswordConfirmation').value;
             if(newpassword.localeCompare(newPasswordConfirmation)!=0)
               {
                 document.getElementById('differentPasswords').style.display="inline";
               }
             else
               {
                  document.getElementById('differentPasswords').style.display="none";
               }
           
          }     
      }; 
      xhr.send();
   }) 
   doTheThing(response)
})()
}


function cheakSupervisorCurrentPassword()
{
(async () => {
   let response = await new Promise(resolve => {
      var xhr = new XMLHttpRequest();
      xhr.open("GET",'/supervisors/check/password',true);
      xhr.setRequestHeader('currentPassword',document.getElementById('currentPassword').value);
      xhr.setRequestHeader('Authorization','Bearer i989saiksjaw98pp;l;l9i89U8U8azAUH');   
      xhr.responseType = 'json';
      xhr.onload = function() {
        const checkCurrentPassword = xhr.response;
        if(checkCurrentPassword.message.localeCompare("correct")==0)
          {      
             document.getElementById('incorrectPassword').style.display="none";
             var newpassword =document.getElementById('newPassword').value;
             var newPasswordConfirmation =document.getElementById('newPasswordConfirmation').value;
             if(newpassword.localeCompare(newPasswordConfirmation)!=0)
               {
                 document.getElementById('differentPasswords').style.display="inline";
               }
             else
               {
                  document.getElementById('differentPasswords').style.display="none";
                  document.getElementById("updatePwdForm").submit();
               }
          }
          else
          {
             document.getElementById('incorrectPassword').style.display="inline";
             var newpassword =document.getElementById('newPassword').value;
             var newPasswordConfirmation =document.getElementById('newPasswordConfirmation').value;
             if(newpassword.localeCompare(newPasswordConfirmation)!=0)
               {
                 document.getElementById('differentPasswords').style.display="inline";
               }
             else
               {
                  document.getElementById('differentPasswords').style.display="none";
               }
           
          }     
      }; 
      xhr.send();
   }) 
   doTheThing(response)
})()
}

function cheakCollaboratorCurrentPassword()
{
(async () => {
   let response = await new Promise(resolve => {
      var xhr = new XMLHttpRequest();
      xhr.open("GET",'/collaborators/check/password',true);
      xhr.setRequestHeader('currentPassword',document.getElementById('currentPassword').value);
      xhr.setRequestHeader('Authorization','Bearer i989saiksjaw98pp;l;l9i89U8U8azAUH');
      xhr.responseType = 'json';
      xhr.onload = function() {
       const checkCurrentPassword = xhr.response;
        if(checkCurrentPassword.message.localeCompare("correct")==0)
          {      
             document.getElementById('incorrectPassword').style.display="none";
             var newpassword =document.getElementById('newPassword').value;
             var newPasswordConfirmation =document.getElementById('newPasswordConfirmation').value;
             if(newpassword.localeCompare(newPasswordConfirmation)!=0)
               {
                 document.getElementById('differentPasswords').style.display="inline";
               }
             else
               {
                  document.getElementById('differentPasswords').style.display="none";
                  document.getElementById("updatePwdForm").submit();
               }
          }
          else
          {
             document.getElementById('incorrectPassword').style.display="inline";
             var newpassword =document.getElementById('newPassword').value;
             var newPasswordConfirmation =document.getElementById('newPasswordConfirmation').value;
             if(newpassword.localeCompare(newPasswordConfirmation)!=0)
               {
                 document.getElementById('differentPasswords').style.display="inline";
               }
             else
               {
                  document.getElementById('differentPasswords').style.display="none";
               }
           
          } 
      }; 
      xhr.send();
   }) 
   doTheThing(response)
})()
}

function checkCollaboratorEmail()
{
(async () => {
   let response = await new Promise(resolve => {
      var xhr = new XMLHttpRequest();
      xhr.open("GET",'/collaborators/check/email',true);
      xhr.setRequestHeader('collaboratorEmail',document.getElementById('email').value);
      xhr.setRequestHeader('Authorization','Bearer i989saiksjaw98pp;l;l9i89U8U8azAUH');
      xhr.responseType = 'json';
      xhr.onload = function(e) {
          const CheckEmailResponse = xhr.response;
          if(CheckEmailResponse.message.localeCompare("true")==0)
            {      
               document.getElementById('emailExists').style.display="inline";
            }
            else
            {
             document.getElementById('emailExists').style.display="none";
             document.getElementById("createCollaborator").submit();
            }
      }; 
      xhr.send();
   }) 
   doTheThing(response)
})()
}

function checkClientEmail()
{
(async () => {
   let response = await new Promise(resolve => {
      var xhr = new XMLHttpRequest();
      xhr.open("GET",'/clients/check/email',true);
      xhr.setRequestHeader('clientEmail',document.getElementById('email').value);
      xhr.setRequestHeader('Authorization','Bearer i989saiksjaw98pp;l;l9i89U8U8azAUH');
      xhr.responseType = 'json';
      xhr.onload = function(e) {
          const CheckEmailResponse = xhr.response;
          if(CheckEmailResponse.message.localeCompare("true")==0)
            {      
               document.getElementById('emailExists').style.display="inline";
            }
            else
            {
             document.getElementById('emailExists').style.display="none";
             document.getElementById("createClient").submit();
            }
      }; 
      xhr.send();
   }) 
   doTheThing(response)
})()
}

function cheakCurrentPassword()
{
(async () => {
   let response = await new Promise(resolve => {
      var xhr = new XMLHttpRequest();
      xhr.open("GET",'/clients/check/password',true);
      xhr.setRequestHeader('currentPassword',document.getElementById('currentPassword').value);
      xhr.setRequestHeader('Authorization','Bearer i989saiksjaw98pp;l;l9i89U8U8azAUH');
      xhr.responseType = 'json';
      xhr.onload = function() {
          const checkCurrentPassword = xhr.response;
          if(checkCurrentPassword.message.localeCompare("correct")==0)
            {      
               document.getElementById('incorrectPassword').style.display="none";
               var newpassword =document.getElementById('newPassword').value;
               var newPasswordConfirmation =document.getElementById('newPasswordConfirmation').value;
               if(newpassword.localeCompare(newPasswordConfirmation)!=0)
                 {
                   document.getElementById('differentPasswords').style.display="inline";
                 }
               else
                 {
                    document.getElementById('differentPasswords').style.display="none";
                    document.getElementById("updatePwdForm").submit();
                 }
            }
            else
            {
               document.getElementById('incorrectPassword').style.display="inline";
               var newpassword =document.getElementById('newPassword').value;
               var newPasswordConfirmation =document.getElementById('newPasswordConfirmation').value;
               if(newpassword.localeCompare(newPasswordConfirmation)!=0)
                 {
                   document.getElementById('differentPasswords').style.display="inline";
                 }
               else
                 {
                    document.getElementById('differentPasswords').style.display="none";
                 }
             
            }  
      }; 
      xhr.send();
   }) 
   doTheThing(response)
})()
}






function mailTo() {

let xhr = new XMLHttpRequest();
xhr.open('GET', "/mailto", true);
xhr.setRequestHeader('ticketNumber',document.getElementById("ticketNum").innerText)+"'";
xhr.setRequestHeader('Authorization','Bearer i989saiksjaw98pp;l;l9i89U8U8azAUH');
xhr.send("foo=bar&lorem=ipsum");

}



function annuler()
{
document.getElementById('image').value = "";
 document.getElementById('importer').style.display="inline";
 document.getElementById('annuler').style.display="none";
 document.getElementById('envoyerImage').style.display="none";
 document.getElementById('pic').style.display="none";
 
}

function imagePreview(){
 document.getElementById('importer').style.display="none";
 document.getElementById('annuler').style.display="inline";
 document.getElementById('envoyerImage').style.display="inline";
  document.getElementById('pic').style.display="inline";
}

function loginFormResizing() {
    var pageWidth = document.documentElement.clientWidth;
    if (pageWidth < 752) {
        document.getElementById('loginForm').style.marginLeft = '25%';
        document.getElementById('loginForm').style.marginRight = '25%';
        document.getElementById('loginForm').style.maxWidth = '50%';
        document.getElementById('loginForm').style.marginTop='20%';
        
    } else {
        document.getElementById('loginForm').style.marginLeft = '30%';
        document.getElementById('loginForm').style.marginRight = '30%';
        document.getElementById('loginForm').style.maxWidth = '40%';
        document.getElementById('loginForm').style.marginTop='10%';
    }

}

$(function(){
        
  var today = new Date();
  var dd = today.getDate();
  var mm = today.getMonth()+1; //January is 0!
  
  var yyyy = today.getFullYear();
  if(dd<10){dd='0'+dd} if(mm<10){mm='0'+mm} today = mm+'/'+dd+'/'+yyyy;
        });

(function ($) {
  "use strict";

  // Preloader
  $(window).on('load', function () {
    if ($('#preloader').length) {
      $('#preloader').delay(100).fadeOut('slow', function () {
        $(this).remove();
      });
    }
  });

  // Back to top button
  $(window).scroll(function() {
    if ($(this).scrollTop() > 100) {
      $('.back-to-top').fadeIn('slow');
    } else {
      $('.back-to-top').fadeOut('slow');
    }
  });
  $('.back-to-top').click(function(){
    $('html, body').animate({scrollTop : 0},1500, 'easeInOutExpo');
    return false;
  });

  // Initiate the wowjs animation library
  new WOW().init();

  // Initiate superfish on nav menu
  $('.nav-menu').superfish({
    animation: {
      opacity: 'show'
    },
    speed: 400
  });

  // Mobile Navigation
  if ($('#nav-menu-container').length) {
    var $mobile_nav = $('#nav-menu-container').clone().prop({
      id: 'mobile-nav'
    });
    $mobile_nav.find('> ul').attr({
      'class': '',
      'id': ''
    });
    $('body').append($mobile_nav);
    $('body').prepend('<button type="button" id="mobile-nav-toggle"><i class="fa fa-bars"></i></button>');
    $('body').append('<div id="mobile-body-overly"></div>');
    $('#mobile-nav').find('.menu-has-children').prepend('<i class="fa fa-chevron-down"></i>');

    $(document).on('click', '.menu-has-children i', function(e) {
      $(this).next().toggleClass('menu-item-active');
      $(this).nextAll('ul').eq(0).slideToggle();
      $(this).toggleClass("fa-chevron-up fa-chevron-down");
    });

    $(document).on('click', '#mobile-nav-toggle', function(e) {
      $('body').toggleClass('mobile-nav-active');
      $('#mobile-nav-toggle i').toggleClass('fa-times fa-bars');
      $('#mobile-body-overly').toggle();
    });

    $(document).click(function(e) {
      var container = $("#mobile-nav, #mobile-nav-toggle");
      if (!container.is(e.target) && container.has(e.target).length === 0) {
        if ($('body').hasClass('mobile-nav-active')) {
          $('body').removeClass('mobile-nav-active');
          $('#mobile-nav-toggle i').toggleClass('fa-times fa-bars');
          $('#mobile-body-overly').fadeOut();
        }
      }
    });
  } else if ($("#mobile-nav, #mobile-nav-toggle").length) {
    $("#mobile-nav, #mobile-nav-toggle").hide();
  }

  // Header scroll class
$(window).scroll(function() {
  });

  if ($(window).scrollTop() > 100) {
    $('#header').addClass('header-scrolled');
  }

  // Smooth scroll for the menu and links with .scrollto classes
  $('.nav-menu a, #mobile-nav a, .scrollto').on('click', function() {
    if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
      var target = $(this.hash);
      if (target.length) {
        var top_space = 0;

        if ($('#header').length) {
          top_space = $('#header').outerHeight();

          if (! $('#header').hasClass('header-scrolled')) {
            top_space = top_space - 20;
          }
        }

        $('html, body').animate({
          scrollTop: target.offset().top - top_space
        }, 1500, 'easeInOutExpo');

        if ($(this).parents('.nav-menu').length) {
          $('.nav-menu .menu-active').removeClass('menu-active');
          $(this).closest('li').addClass('menu-active');
        }

        if ($('body').hasClass('mobile-nav-active')) {
          $('body').removeClass('mobile-nav-active');
          $('#mobile-nav-toggle i').toggleClass('fa-times fa-bars');
          $('#mobile-body-overly').fadeOut();
        }
        return false;
      }
    }
  });

  // Navigation active state on scroll
  var nav_sections = $('section');
  var main_nav = $('.nav-menu, #mobile-nav');
  var main_nav_height = $('#header').outerHeight();

  $(window).on('scroll', function () {
    var cur_pos = $(this).scrollTop();
  
    nav_sections.each(function() {
      var top = $(this).offset().top - main_nav_height,
          bottom = top + $(this).outerHeight();
  
      if (cur_pos >= top && cur_pos <= bottom) {
        main_nav.find('li').removeClass('menu-active menu-item-active');
        main_nav.find('a[href="#'+$(this).attr('id')+'"]').parent('li').addClass('menu-active menu-item-active');
      }
    });
  });

  // Intro carousel
  var introCarousel = $(".carousel");
  var introCarouselIndicators = $(".carousel-indicators");
  introCarousel.find(".carousel-inner").children(".carousel-item").each(function(index) {
    (index === 0) ?
    introCarouselIndicators.append("<li data-target='#introCarousel' data-slide-to='" + index + "' class='active'></li>") :
    introCarouselIndicators.append("<li data-target='#introCarousel' data-slide-to='" + index + "'></li>");

    $(this).css("background-image", "url('" + $(this).children('.carousel-background').children('img').attr('src') +"')");
    $(this).children('.carousel-background').remove();
  });

  $(".carousel").swipe({
    swipe: function(event, direction, distance, duration, fingerCount, fingerData) {
      if (direction == 'left') $(this).carousel('next');
      if (direction == 'right') $(this).carousel('prev');
    },
    allowPageScroll:"vertical"
  });

  // Skills section
  $('#skills').waypoint(function() {
    $('.progress .progress-bar').each(function() {
      $(this).css("width", $(this).attr("aria-valuenow") + '%');
    });
  }, { offset: '80%'} );

  // jQuery counterUp (used in Facts section)
  $('[data-toggle="counter-up"]').counterUp({
    delay: 10,
    time: 1000
  });

  // Porfolio isotope and filter
  var portfolioIsotope = $('.portfolio-container').isotope({
    itemSelector: '.portfolio-item',
    layoutMode: 'fitRows'
  });

  $('#portfolio-flters li').on( 'click', function() {
    $("#portfolio-flters li").removeClass('filter-active');
    $(this).addClass('filter-active');

    portfolioIsotope.isotope({ filter: $(this).data('filter') });
  });

  // Clients carousel (uses the Owl Carousel library)
  $(".clients-carousel").owlCarousel({
    autoplay: true,
    dots: true,
    loop: true,
    responsive: { 0: { items: 2 }, 768: { items: 4 }, 900: { items: 6 }
    }
  });

  // Testimonials carousel (uses the Owl Carousel library)
  $(".testimonials-carousel").owlCarousel({
    autoplay: true,
    dots: true,
    loop: true,
    items: 1
  });

})(jQuery);


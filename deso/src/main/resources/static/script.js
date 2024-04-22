const headerTexts = document.querySelectorAll(".header .text")
// 서비스 소개
const introduceService = document.querySelector('.section1 .catchphrase')
const locationIntroduceService = introduceService.offsetTop
headerTexts[0].onclick = function(){
  window.scroll({top: locationIntroduceService, behavior: 'smooth'})
}
// 기능 소개
const introFunction = document.querySelector('.section3 .container .sub-title')
headerTexts[1].onclick = function(){
  window.scroll({top: introFunction.offsetTop, behavior: 'smooth'})
}
// 활용 사례
const example = document.querySelector('.section5 .container')
headerTexts[2].onclick = function(){
  window.scroll({top: example.offsetTop, behavior: 'smooth'})
}
// 이용 후기
const review = document.querySelector('.section6')
headerTexts[3].onclick = function(){
  window.scroll({top: review.offsetTop, behavior: 'smooth'})
}
// 맨 위로 이동
const moveTopBtn = document.querySelector('#move-top')
const locationHeader = document.querySelector('.header').offsetTop
moveTopBtn.onclick = function(){
  window.scroll({top: locationHeader, behavior: 'smooth'})
}
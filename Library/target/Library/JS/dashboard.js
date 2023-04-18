
const common ='http://localhost:8080/Library/services/';
const totalbooks= document.getElementById("fortotalbooks");
const totalusers = document.getElementById("fortotalusers");
const totaldue = document.getElementById("totaldues");


let url1=common+'books/totalbooks';
let url2= common+'students/totalstudents';
let url3=common+'userandbook/totaldues';

function downloadexcel(){
  const a= document.getElementById("foryear");
  let b= a.value;
  console.log(b);
  window.location.href= common+`userandbook/exceldownload/${b}`;
}
function gotohome(){
    window.location.href="index.html";
}
async function get1(url) {
    let x = await fetch(url);
    let y = await x.text();
    console.log(y);
    totalbooks.innerText = y;
  }

  async function get2(url) {
    let x = await fetch(url);
    let y = await x.text();
    console.log(y);
    totalusers.innerText = y;
  }
  async function get3(url) {
    let x = await fetch(url);
    let y = await x.text();
    console.log(y);
    totaldue.innerText = y;
  }

  
  get1(url1);
  get2(url2);
  get3(url3);

  var categories={};
  categories.Fiction=0;
  categories.Horror=0;
  categories.ScienceFiction=0;
  categories.Crime=0;
  categories.Education=0;
  categories.Sports=0;
  var total=0;
 async function genre(){
    await  fetch(common+'bookissue') 
    .then((response)=> response.json())
    .then((json)=>{console.log(json.Fiction);
     categories.Fiction=json.Fiction;
     console.log(json.ScienceFiction);
     categories.Horror=json.Horror;
     categories.ScienceFiction=json.ScienceFiction;
     categories.Crime=json.Crime;
     categories.Education=json.Education;
     categories.Sports=json.Sports;
  }  ) ;
   displaychart();
  }
  genre();
  
  function displaychart(){
    var a= categories.Fiction;
    var b= categories.Horror;
    var c= categories.ScienceFiction;
    var d= categories.Crime;
    var e=  categories.Education;
    var f=   categories.Sports;
    var xValues = ["Fiction", "Horror", "ScienceFiction", "Crime", "Education","Sports"];
    Chart.defaults.global.defaultFontColor = "";
    
     var yValues = [a,b,c,d,e,f];
    //var yValues=[5,4,3,2,1];
    var barColors = [
      "#D4F1F4",
      "#75E6DA",
      "#189AB4",
      "#05445E",
      "#f4f0ee",
      "#18b473"
      

    ];
    
    new Chart("myChart", {
      type: "pie",
      data: {
        labels: xValues,
        datasets: [{
          backgroundColor: barColors,
          data: yValues
        }]
      },
      options: {
        title: {
          display: true,
          text: "Genre of Books Issued",
        }
       },
      options: {
        legend :{
          horizontalAlign: "right",
          }
      }
    });
  }
displaychart();



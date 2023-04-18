// const common ='http://localhost:8080/Library/services/';
function issueabook(){

    // showbooks();
    const issuebookform = document.getElementById('issueformhide');
issuebookform.style.display="block";
// const book= document.querySelector("books");
// book.classList.remove("active");
// const issuebookbtn = document.querySelector("issuebook");
// issuebookbtn.classList.add("active");
}
function closeissueform(){
    const issuebookform = document.getElementById('issueformhide');
    issuebookform.style.display="none";
    window.location.reload();
 }
function SetDate()
{
var date = new Date();

var day = date.getDate();
var month = date.getMonth() + 1;
var year = date.getFullYear();

if (month < 10) month = "0" + month;
if (day < 10) day = "0" + day;

var today = year + "-" + month + "-" + day;


document.getElementById('issuedate').value = today;
document.getElementById('issuedate').min = today;
}
SetDate();
function issuetime(){
    const date = new Date();
    let hrs = date.getHours()
    hrs = hrs.toString();
   let mins = date.getMinutes();
   mins = mins.toString();
   let seconds = date.getSeconds();
   seconds = seconds.toString();
if(hrs.length == 1){
    hrs = "0"+hrs;
    console.log(hrs);
}

if(mins.length == 1){
    mins = "0"+mins;
    console.log(mins);
}
if(seconds.length == 1){
    seconds = "0"+seconds;
    console.log(seconds);
}
let current_time = `${hrs}:${mins}:${seconds}`;
console.log("time", typeof (current_time));
return current_time;
}


 


// getting all required elements
const searchWrapper = document.querySelector(".search-input");
const inputBox = searchWrapper.querySelector("input");
const suggBox = searchWrapper.querySelector(".autocom-box");
let linkTag = searchWrapper.querySelector("a");


// if user press any key and release
//  inputBox.onkeyup = (e)=>{
//     let myTimeout;
//      let userData = e.target.value; //user entered data
//      let emptyArray = [];
//      SetDate();
     
//      myTimeout = setTimeout(bookmatch(userData),500);
//      clearTimeout(myTimeout);
     
         
         
     
//      if(userData){
//          emptyArray = suggestions.filter((data)=>{
//              //filtering array value and user characters to lowercase and return only those words which are start with user entered chars
//              //bookmatch(data);
//              return data.toLocaleLowerCase().startsWith(userData.toLocaleLowerCase());
//          });
//          emptyArray = emptyArray.map((data)=>{
           
//              // passing return data inside li tag
//              return data = `<li>${data}</li>`;
//          });
//          searchWrapper.classList.add("active"); //show autocomplete box
//          showSuggestions(emptyArray);
//          let allList = suggBox.querySelectorAll("li");
//          for (let i = 0; i < allList.length; i++) {
//              //adding onclick attribute in all li tag
//              allList[i].setAttribute("onclick", "select(this)");
//              console.log(allList[i]);
//              console.log(allList[i].value);
//              var bookName=inputBox.value;
//              console.log(bookName);
         
//          }
//      }else{
//          searchWrapper.classList.remove("active"); //hide autocomplete box
//      }
//  }
var flag=0;
 let suggestions = [];
 fetch(common+'books')
 .then((response)=> response.json())
     .then((json)=>{
         json.forEach(book => {
             console.log(book.bookName);
             if(book.total_copies){
                 suggestions.push(book.bookName);
             }
             else{
                flag=1;
             
             }
         
         });
     })
inputBox.onkeyup = (e)=>{
    let userData = e.target.value; //user entered data
   
    
    if(userData){
                 emptyArray = suggestions.filter((data)=>{
                     //filtering array value and user characters to lowercase and return only those words which are start with user entered chars
                     return data.toLocaleLowerCase().startsWith(userData.toLocaleLowerCase());
                 });
                 emptyArray = emptyArray.map((data)=>{
                    // passing return data inside li tag
                    return data = `<li>${data}</li>`;
                });
                searchWrapper.classList.add("active"); //show autocomplete box
                showSuggestions(emptyArray);
                let allList = suggBox.querySelectorAll("li");
                for (let i = 0; i < allList.length; i++) {
                    //adding onclick attribute in all li tag
                    allList[i].setAttribute("onclick", "select(this)");
                    console.log(allList[i]);
                    console.log(allList[i].value);
                    var bookName=inputBox.value;
                    console.log(bookName);
                
                }
            }else{
                searchWrapper.classList.remove("active"); //hide autocomplete box
            }

}

 function select(element){
     let selectData = element.textContent;
     inputBox.value = selectData;
     searchWrapper.classList.remove("active");
 }
function showSuggestions(list){
    let listData;
    if(!list.length){
        userValue = inputBox.value;
        listData = `<li>${userValue}</li>`;
    }else{
      listData = list.join('');
    }
    suggBox.innerHTML = listData;
}


    
const form = document.getElementById('issue');
  
form.addEventListener('submit', (e)=>{
    e.preventDefault();

    const formData = new FormData(form);
    // const data = new URLSearchParams(formData);
     //Create an object from the form data entries
 

  let formDataObject = Object.fromEntries(formData.entries());
  console.log(formDataObject.issueDate);
    var oldDate = new Date(formDataObject.issueDate);
    console.log( oldDate.toLocaleDateString() + "\n" + oldDate.toISOString() + "\n" +
    oldDate.toDateString() +"\n" + oldDate.toUTCString());
    var newDate = oldDate.toDateString().substring(4,10) + ", " + oldDate.toDateString().substring(11,);
    console.log(newDate);
    formDataObject.issueDate=newDate;
   
    formDataObject.issueTime=issuetime();
    
    let formDataJsonString = JSON.stringify(formDataObject);

    console.log(formDataJsonString);
    fetch(common+'bookissue/insert', {
      method: 'POST',
      headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
         
        },
        body: formDataJsonString
    }).then((response) => response.text())
    .then((text) => {
              var a = text;
              console.log(a);
             // alert(a);
             if(a==="Book Issued"){
                swal("Book issued!", "Mail has been sent.", "success");
             }
             else if(a==="Book Already issued"){
                swal("Book already issued to this user!", "","error");
             }
             else{
                alert("Select Suggested option only!");
             }
            
            //  document.getElementById('issue').requestFullscreen();
            document.getElementById("issue").reset();
            SetDate();
            showissuedbooks();
           
              
    })
        // An important thing to note is that an error response will not throw
    });    
    

//forrolllno searchbar
    var x=0;
    let students = [];
    fetch(common+'students')
    .then((response)=> response.json())
        .then((json)=>{
            json.forEach(student => {
                console.log(student.rollNo);
                if(!student.isDeleted){
                    students.push(student.rollNo);
                }
               
               
            });
        })

// getting all required elements
const searchWrappers = document.querySelector(".search-inputs");
const inputBoxs = searchWrappers.querySelector("input");
const suggBoxs = searchWrappers.querySelector(".autocom-boxs");
let linkTags = searchWrappers.querySelector("a");


// async function bookmatch(a){
//     let reponse=await fetch(common+`books/matchbooks/${a}`)
//     let r = await reponse.json();
//     console.log(typeof(r));
//     var i=0;
//     //    while(i<r.length){
//     //     suggestions.push(r[i]);
//     //     console.log(r[i]);
//     //    }
//        for(bookname in r){
//         console.log(r[bookname]);
//         if(!suggestions.includes(r[bookname]))
//         suggestions.push(r[bookname]);
//        }
     
   
// }

// if user press any key and release
inputBoxs.onkeyup = (e)=>{
    let userData = e.target.value; //user entered data
    let emptyArray = [];
    if(userData){
        emptyArray = students.filter((data)=>{
            //filtering array value and user characters to lowercase and return only those words which are start with user entered chars
            return data.toLocaleLowerCase().startsWith(userData.toLocaleLowerCase());
        });
        emptyArray = emptyArray.map((data)=>{
            
            // passing return data inside li tag
            return data = `<li>${data}</li>`;
        });
        searchWrappers.classList.add("active"); //show autocomplete box
        showSuggestionsS(emptyArray);
        let allList = suggBoxs.querySelectorAll("li");
        for (let i = 0; i < allList.length; i++) {
            //adding onclick attribute in all li tag
            allList[i].setAttribute("onclick", "selectS(this)");
            console.log(allList[i]);
            console.log(allList[i].value);
            var rollNo=inputBoxs.value;
            console.log(rollNo);
           
        }
    }else{
        searchWrappers.classList.remove("active"); //hide autocomplete box
    }
}



 function selectS(element){
     let selectData = element.textContent;
     inputBoxs.value = selectData;
     searchWrappers.classList.remove("active");
 }
function showSuggestionsS(list){
    let listData;
    if(!list.length){
        userValue = inputBoxs.value;
        listData = `<li>${userValue}</li>`;
    }else{
      listData = list.join('');
    }
    suggBoxs.innerHTML = listData;
}


//   throw response;
        // }
    
        // since we expect a json response we will return a json call
        // const res = response.json();
        //  console.log(res);
      //   console.log('Done');
      
//    
    
    
    //     res.then((bookissue)=>{
            
    //         swal(" Issued Successfully!");
    //         document.getElementById("issue").reset();
    //     })
    //   })
    //document.getElementById("issue").reset();
    // an error so if the result is not okay we should throw the error
        // if(!response.ok) {
        

    // const  Enteredbookname= Enteredbookname.value;
    // function showbooksinlist(){
    //     fetch('http://localhost:8080/Library/books')
    //     .then((response)=> response.json())
    //         .then((json)=>{
    //             json.forEach(book => {
    //                 console.log(book.bookName);
    //                 if(book.total_copies){
    //                     suggestions.push(book.bookName);
    //                 }
     
    //                 else{
    //                    flag=1;
                    
    //                 }
                
    //             });
    //         })
     
    //  }
    //  showbooksinlist();
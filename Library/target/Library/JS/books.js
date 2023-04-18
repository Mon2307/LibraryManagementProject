
const common ='http://localhost:8080/Library/services/';

const bookstable= document.getElementById("allbooks");
const issuedbookstable= document.getElementById("issuedbooks");
function gotohome(){
    window.location.href="index.html";
}



function closeaddbook(){
    const addForm=document.getElementById("controladdbook");
    addForm.style.display="none";
}
function validatecost(a){
  if(a<1){
    alert("Cost cannot be 0");
    return false;
  }
  else{
    return true;
  }
}
function validatecopy(b){
  if(b<1){
    alert("Atleast one copy is required!");
    return false;
  }
  else{
    return true;
  }
}

async function validatebookid(x){
  const r= await fetch(common+`/books/ifbook/${x}`);
  let find = await r.json();
  // let y=document.getElementById();
  console.log(find);
  if(find){
    alert('Books Exists Already.');
  }
  else{
    return true;
  }
}
function addbook(){
    const addForm=document.getElementById("controladdbook");
    addForm.style.display="block";
    // document.getElementById("addBook").style.display="block";
    // const open = document.querySelector('.addbookform');
    // open.classList.toggle('addbookopen');
    const form = document.getElementById('addBook');
  
  form.addEventListener('submit', (e)=>{
      e.preventDefault();
      const formData = new FormData(form);
        let formDataObject = Object.fromEntries(formData.entries());
        // if(validatebookid(formDataObject.bookId))
      if((validatebookid(formDataObject.bookId))){
        
       
          let formDataJsonString = JSON.stringify(formDataObject);
          console.log(formDataJsonString);
         
          fetch(common+'books/insert', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                Accept: "application/json",
               
              },
              body: formDataJsonString
          }).then((response) => {
              
              if(!response.ok) {
                throw response;
              }
          
             
              const res = response.json();
               console.log(res);
            //   console.log('Done');
              res.then((book)=>{
                  console.log(book.bookName);
                  swal(book.bookName + " Added Successfully!")
                  document.getElementById("addBook").reset();
              })
            })

          }
      });

    } 
     
    



function showbooks(){
    bookstable.style.display="block";
    issuedbookstable.style.display="none";
    const issue= document.getElementById("active");
    const book =document.getElementById("books");
    book.style.background="#189ab4";
    issue.style.background="black";
    const api_url =common+'books';

// Defining async function
async function getapi(url) {
	
	// Storing response
	const response = await fetch(url);
	
	// Storing data in form of JSON
	var data = await response.json();
	console.log(data);
	if (response) {
		show(data);
	}
    else{
        loader();
    }
	
}
// Calling that async function
getapi(api_url);

// Function to hide the loader
function loader() {
	document.getElementById('loading').style.display;
}
// Function to define innerHTML for HTML table
function show(data) {
	let tab =
		`
        <thead>
        <tr>
        <th>Book No</th>
		<th>Book Name</th>
		<th>Author</th>
		<th>Publication</th>
    <th>Count</th>
    </thead>
		</tr>`;
	
	// Loop to access all rows
	for (let r of data) {
		tab += `<tbody><tr>
  <td>${r.bookId}</td>
	<td>${r.bookName} </td>
	<td>${r.authorName}</td>
	<td>${r.publication}</td>
  <td>${r.total_copies}</td>			
</tr></tbody>`;
	}
	// Setting innerHTML as tab variable
	document.getElementById("Books").innerHTML = tab;
}


}


function showissuedbooks(){
  //const bookbtn = document.getElementsByClassName('active');
//  bookbtn.classList.remove("active");

//  const issuedbooks =document.querySelector('issuebook');
 //issuedbooks.classList.add("active");
    bookstable.style.display="none";
    issuedbookstable.style.display="block";
   const issue= document.getElementById("active");
   const book =document.getElementById("books");
   book.style.background="black";
   issue.style.background="#189ab4";
   

    const api_url =common+'bookissue/bookissued';

// Defining async function
async function getapi(url) {
	
	// Storing response
	const response = await fetch(url);
	
	// Storing data in form of JSON
	var data = await response.json();
	console.log(data);
	if (response) {
		show(data);
	}
    else{
        loader();
    }
	
}
// Calling that async function
getapi(api_url);

// Function to hide the loader
function loader() {
	document.getElementById('loading').style.display;
}
// Function to define innerHTML for HTML table
function show(data) {
  
	let tab =
		`<tr>
		<th>Roll No</th>
		<th>Book Name</th>
		<th>Issued Date</th>
        <th>Returned Status</th>
		</tr>`;
	
	// Loop to access all rows
	for (let r of data) {
		tab += `<tr>
  <td>${r.studentRollNo}</td>
	<td>${r.bookName} </td>
	<td>${r.issueDate}</td>  
    <td><button class="returnbtn btn btn primary" onclick="returnbook(this)">Return</button></td>		
</tr>`;
	}
	// Setting innerHTML as tab variable
	document.getElementById("issuedBooks").innerHTML = tab;
}

}

function myFunction() {
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById("search");
  filter = input.value.toUpperCase();
  table = document.getElementById("Books");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[1];
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }       
  }
}

function SetreturnDate()
{
var date = new Date();

var day = date.getDate();
var month = date.getMonth() + 1;
var year = date.getFullYear();

if (month < 10) month = "0" + month;
if (day < 10) day = "0" + day;

var today = year + "-" + month + "-" + day;
var oldDate = new Date(today);
    console.log( oldDate.toLocaleDateString() + "\n" + oldDate.toISOString() + "\n" +
    oldDate.toDateString() +"\n" + oldDate.toUTCString());
    var newDate = oldDate.toDateString().substring(4,10) + ", " + oldDate.toDateString().substring(11,);
    console.log(newDate);
return newDate;
}

function returnbook(e){
    var uTable = document.getElementById('issuedBooks');

      let index = e.parentNode.parentNode.rowIndex;
      var Cells = uTable.rows.item(index).cells;
     let studentRollNo = Cells[0].innerText;
     let  bookName = Cells[1].innerText;
     console.log(index);
     console.log(Cells);
     console.log(studentRollNo);
     console.log(bookName);

     var formDataObject={};
     formDataObject.returnDate= SetreturnDate();
     formDataObject.bookName= bookName;
     formDataObject.studentRollNo=studentRollNo;
     //formDataObject.studentRollNo=
     console.log(formDataObject);
     
     let formDataJsonString = JSON.stringify(formDataObject);
     console.log(formDataJsonString);
      fetch(common+'bookreturn/insert', {
           method: 'POST',
           headers: {
               "Content-Type": "application/json",
               Accept: "application/json",
              
             },
             body: formDataJsonString
         }).then((response) => {
             
             if(!response.ok) {
               throw response;
             }
         
            
             const res = response.json();
              console.log(res);
           
           res.then((bookreturn)=>{
            returnDues(formDataJsonString);
            swal(" Returned Successfully!");
            showissuedbooks();
               
             })
     
             
           })
     }



     function returnDues(formDataJsonString){
      fetch(common+'bookreturn/getdues', {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
           
          },
          body: formDataJsonString
      }).then((response) => {
          // An important thing to note is that an error response will not throw
          // an error so if the result is not okay we should throw the error
          if(!response.ok) {
            throw response;
          }
      
          // since we expect a json response we will return a json call
          const res = response.json();
           console.log(res);
        //   console.log('Done');
        res.then((dues)=>{
            console.log(dues);
           // swal(" Returned Successfully!");
              //document.getElementById("return").reset();
          })
  
          
        })
    
    }
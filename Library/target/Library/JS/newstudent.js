const common ='http://localhost:8080/Library/services/';
// const changeBackground = document.querySelector('.forbackground');
//const formchange= document.querySelector('.newstudent');
function closestudent(){
    // formchange.classList.remove('openform');
    // formchange.classList.add('closeform');
    const addstudentform= document.getElementById('controladdstudent');
   addstudentform.style.display="none";
 
//    changeBackground.classList.toggle('forbackground');
}
function openstudent(){
  // formchange.classList.remove('openform');
  // formchange.classList.add('closeform');
  const addstudentform= document.getElementById('controladdstudent');
   addstudentform.style.display="block";

//    changeBackground.classList.toggle('forbackground');
}

function gotohome(){
    window.location.href="index.html";
}
async function checkstudent(x){
  const r= await fetch(common+`students/${x}`);
  let find = await r.json();
  // let y=document.getElementById();
  console.log(find);
  if(find){
    alert('Student Exists Already.');
    const m = document.getElementById('addstudent');
    m.reset();
    return;
  }
  else{
    return true;
  }
}
function Addstudent(){
    // changeBackground.classList.toggle('afterformopen');
    // formchange.classList.toggle('openform');
    openstudent();
  const form = document.getElementById('addstudent');
  
  form.addEventListener('submit', (e)=>{
      e.preventDefault();
  
      const formData = new FormData(form);
      // const data = new URLSearchParams(formData);
       //Create an object from the form data entries
    let formDataObject = Object.fromEntries(formData.entries());
      // Format the plain form data as JSON

      // for(field in formDataObject ){
      //   if(!field.value){
      //     alert(field+" cannot be empty");
      //   }
      //  }
      
      
      if(checkstudent(formDataObject.rollNo)){ let formDataJsonString = JSON.stringify(formDataObject);
  
        console.log(formDataJsonString);
        fetch(common+'students/insert', {
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
            res.then((student)=>{
                
                console.log(student.studentName);
                swal(student.studentName +" Added Successfully!");
              //   if(book.Name != 0){
              //       // alert("succesfully logged in")
              //       window.location.href = "overview.html"
              //   }else{
              //       alert("Wrong credentials")
              //   }
              document.getElementById("addstudent").reset();
              //window.location.reload();
              
            })
          })
    }
  });
}
     

// api url

var rollno=[];
const api_url =common+'students';

// Defining async function
async function getapi(url) {
	
	// Storing response
	const response = await fetch(url);
	
	// Storing data in form of JSON
	var data = await response.json();
	console.log(data);

    for(r of data){
        console.log(r);
        console.log(r.branchId);
        console.log(r.rollNo);
        rollno.push(r.rollNo);
       if(r.branchId==1){
        r.Branch="CS";
        console.log(r.Branch);
       }
       else if(r.branchId==2){
        r.Branch="IT";
        console.log(r.Branch);
       }
       else if(r.branchId==3){
        r.Branch="EE";
        console.log(r.Branch);
       }
       else if(r.branchId==4){
        r.Branch="ENTC";
        console.log(r.Branch);
       }
       else if(r.branchId==5){
        r.Branch="ME";
        console.log(r.Branch);
       }
        
    }
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
		<th>Email Id</th>
		<th>Name</th>
        <th>Branch</th>
        <th></th>
        <th></th>
		</tr>`;
	// Loop to access all rows
	for (let r of data) {
		tab += `<tr>
  <td>${r.rollNo}</td>
	<td>${r.mailId} </td>
	<td>${r.studentName}</td>
    <td>${r.Branch}</td>
    <td><button class="btn"onclick="deletestudent(this)"><img src=icons/deleteicon.png></button></td>	
    <td><button class="btn  showdues" onclick="showdues(this)"> Show Dues </button></td>			
</tr>`;
	}

  //<td><button class="btn btn-primary" onclick="editstudent(this)">Edit</button></td>
	// Setting innerHTML as tab variable
	document.getElementById("Students").innerHTML = tab;
}

async function deletestudent(e){
  var sTable = document.getElementById('Students');
  console.log(e.parentNode);
  console.log(e.parentNode.parentNode);
  let r= e.parentNode.parentNode;
  console.log(e.parentNode.parentNode.rowIndex);
  let index = e.parentNode.parentNode.rowIndex;
  var Cells = sTable.rows.item(index).cells;
  let studentRollNo = Cells[0].innerText;
  console.log(studentRollNo);
     const a= await fetch(common+`students/onestudent/${studentRollNo}`);
     var data= await a.json();
     
	console.log(data);

   
     console.log(data.studentId);
     console.log(data.rollNo);

     const updatedata={
      studentId: data.studentId,
      rollNo: data.rollNo,
      mailId: data.mailId,
      branchId: data.branchId,
      genderId: data.genderId,
      studentName: data.studentName,
      isDeleted: true
  } 
 
    const sendstudent= JSON.stringify(updatedata);
  fetch(common+'students/updatestudent', {
    method: 'PUT',
    headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
       
      },
      body: sendstudent
  }).then((response)=>{
    console.log(response);
  },
     

  )
  window.location.reload();
}


function myFunction() {
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById("search");
  filter = input.value.toUpperCase();
  table = document.getElementById("Students");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[0];
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


async function showdues(e){
  var sTable = document.getElementById('Students');
  console.log(e.parentNode);
  console.log(e.parentNode.parentNode);
  console.log(e.parentNode.parentNode.rowIndex);
  let index = e.parentNode.parentNode.rowIndex;
  var Cells = sTable.rows.item(index).cells;
  let studentRollNo = Cells[0].innerText;
  const res= await fetch(common+`userandbook/studentdue/${studentRollNo}`);
  console.log(res);
  var data = await res.json();
  console.log(data);
  if(data.length===0){
    alert("No Books issued");
   
  }
  else{
   
    
    var modal = document.getElementById("myModal");
    modal.style.display = "block";
    var span = document.getElementsByClassName("close")[0];
    span.onclick = function() {
      modal.style.display = "none";
    }
    window.onclick = function(event) {
      if (event.target == modal) {
        modal.style.display = "none";
      }
    }
   
      let tab =
      `<tr>
      <th>Roll No</th>
      <th>Book Name</th>
      <th>Dues</th>
      <th>Issue Date</th>
      <th>Return Date</th>
     
      </tr>`;
    // Loop to access all rows
    for (let r of data) {
      if(r.returnDate){
        tab += `<tr>
        <td>${r.rollno}</td>
        <td>${r.bookname} </td>
        <td>${r.dues}</td>
          <td>${r.issueDate}</td>
          <td>${r.returnDate}</td>	
      </tr>`
      }
      else{
        tab += `<tr>
        <td>${r.rollno}</td>
        <td>${r.bookname} </td>
        <td>${r.dues}</td>
          <td>${r.issueDate}</td>
          <td>-</td>	
      </tr>`
      }
     
     
    }
    document.getElementById("duestable").innerHTML = tab;
   
  }
 
  
}
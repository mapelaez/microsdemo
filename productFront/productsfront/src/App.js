import logo from './logo.svg';
import React, { useState, useEffect} from 'react';

export default function ProductList() {
  const [products, setProducts] = useState([]);
  const [selectedValue, setSelectedValue] = useState('');
  const [productId, setProductId] = useState('');
  const [name, setName] = useState('');
  const [description, setDescription] = useState('')
  const [type, setType] = useState('')



  function getData(){
    const data ={
      name: name ||null,
      description: description || null,
      type: type || null
    };
    return data
  }

  function getProduct(){
    if (!productId) {
      console.error('Product ID is empty');
      return;
    }
    fetch("/product/"+productId)
      .then((response) => {
        return response.json()
      })
      .then((data) => {
        console.log("DATA: ",data)
        const product = data
        if (data.message && data.status === 404){
          console.error(`Error: ${data.message}`);
          setProducts([])
        }
        else{
          setProducts([product])
        }
      } )
      .catch((error) => {
        console.error('Fetch error:', error)
      })
  }

  function getProducts(){
    fetch("/product/products")
      .then((response) =>{
        return response.json()})
      .then((data) =>{
        const product = data._embedded.productEntityList
        console.log("DATA: ",data)
        if (data.message && data.status === 404) {
          console.error(`Error: ${data.message}`);
          setProducts([])
        }
        else{
          setProducts(product)
        }
      })
      .catch((error) => {
        console.error('Fetch error:', error)
      })
  }

  function putProduct(){
    const data = getData()
    fetch(("/product"),{
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((response) =>
        { 
        return response.json()})
      .then(() => {
        getProducts()
      })
      .catch((error) => {
        console.error('Add error:', error)
      })
  } 

  function delProduct(){
    fetch(("/product/"+productId), {  
      method: 'DELETE',
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Delete request failed");
        }
      })
      .then(() => {
        getProducts()
      })
      .catch((error) => {
        console.error('Delete request error', error)
      })
  }

  function updateProduct(){
    const data = getData()
    
    fetch(("/product/"+productId),{
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })

      .then((response) =>{
        return response.json()
      })
      .then(() => {
        getProducts()})
      .catch((error) => 
        console.error('Update request error', error))
  }
      
  function handleSubmit(event){
    event.preventDefault()

    if (selectedValue === "GET"){
      getProduct()
    }
    else if(selectedValue === "GETALL"){
      getProducts()
    }
    else if (selectedValue === "POST"){
      putProduct()
    }
    else if (selectedValue == "DEL"){
      delProduct()
    }
    else if (selectedValue == "UPDATE"){
      updateProduct()
    }
  }

  function handleRadioChange(event){
    setSelectedValue(event.target.value)
    console.log('Selected Value:', event.target.value)
  }

  function handleProductIdChange(event){
    setProductId(event.target.value)
    console.log('id:', event.target.value)
  }
  
  function handleNameChange(event){
    setName(event.target.value)
    console.log('name:', event.target.value)
  }

  function handleDescriptionChange(event){
    setDescription(event.target.value)
    console.log('description:', event.target.value)
  }

  function handleTypeChange(event){
    setType(event.target.value)
    console.log('type: ', event.target.value)
  }

  return (
    <div>
      <h1>Product Manager</h1>
      <ul>
        {products.length === 0 ? (
          <li> No products found</li>
        ) :
        products.map((product) => (
          <li key={product.id}>ID: {product.id}&nbsp;&nbsp;&nbsp;NAME: {product.name}</li>
        ))}
      </ul>
      <form  onSubmit={handleSubmit}>
          <label htmlFor="id"> Product ID: </label>
          <input 
            type="number" 
            id="id"
            name="id"
            value={productId} 
            onChange={handleProductIdChange}/><br></br>
          <label htmlFor="name"> Product Name: </label>
          <input 
            type="text" 
            id="name" 
            name="name"
            value={name}
            onChange={handleNameChange} /><br></br>
          <label htmlFor="description"> Description: </label>
          <input 
            type="text" 
            id="description" 
            name="description"
            value={description}
            onChange={handleDescriptionChange} /> <br></br>
          <label htmlFor="type"> Type: </label>
          <input 
            type="text" 
            id="type"
            name="type"
            value={type}
            onChange={handleTypeChange}/><br></br><br></br>
          <label htmlFor="action"> Actions: </label> <br></br>
          <input 
            type = "radio" 
            id = "get" 
            name = "rest" 
            value = "GET" 
            checked = {selectedValue === "GET"}
            onChange={handleRadioChange}/> 
          <label htmlFor="GET">GET PRODUCT</label><br></br>
          <input 
            type="radio" 
            id="getall" 
            name="rest" 
            value="GETALL"
            checked={selectedValue === "GETALL"}
            onChange={handleRadioChange} /> 
          <label htmlFor="GETALL">GET ALL PRODUCTS</label><br></br>
          <input 
            type="radio" 
            id="add" 
            name="rest" 
            value="POST" 
            checked={selectedValue === "POST"}
            onChange={handleRadioChange}/> 
          <label htmlFor="ADD">ADD PRODUCT</label><br></br>
          <input 
            type="radio" 
            id="delete" 
            name="rest" 
            value="DEL" 
            checked={selectedValue === "DEL"}
            onChange={handleRadioChange} /> 
          <label htmlFor="DELETE">DELETE PRODUCT</label><br></br>
          <input 
            type="radio" 
            id="update" 
            name="rest" 
            value="UPDATE" 
            checked={selectedValue === "UPDATE"}
            onChange={handleRadioChange}/>
          <label htmlFor="UPDATE">UPDATE PRODUCT</label><br></br>
          <button> Submit </button>
      </form>
    </div>
  );
}
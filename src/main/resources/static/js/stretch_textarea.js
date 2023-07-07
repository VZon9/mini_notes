const tx = document.querySelector("textarea");
tx.addEventListener("keyup", e =>{
    tx.style.height = "auto";
    let scHeight = e.target.scrollHeight;
    tx.style.height = (scHeight) + 'px';
});

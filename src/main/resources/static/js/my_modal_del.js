// Открыть модальное окно
document.getElementById("open-my-modal-del").addEventListener("click", function() {
    document.getElementById("my-modal-del").classList.add("open")
});

// Закрыть модальное окно
document.getElementById("close-my-modal-del").addEventListener("click", function() {
    document.getElementById("my-modal-del").classList.remove("open")
});

// Закрыть модальное окно при клике вне его
document.querySelector("#my-modal-del .my_modal_box_del").addEventListener('click', event => {
    event._isClickWithInModal = true;
});
document.getElementById("my-modal-del").addEventListener('click', event => {
    if (event._isClickWithInModal) return;
    event.currentTarget.classList.remove('open');
});
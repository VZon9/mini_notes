// Открыть модальное окно
document.getElementById("open-my-modal").addEventListener("click", function() {
    document.getElementById("my-modal").classList.add("open")
});

// Закрыть модальное окно
document.getElementById("close-my-modal").addEventListener("click", function() {
    document.getElementById("my-modal").classList.remove("open")
});

// Закрыть модальное окно при клике вне его
document.querySelector("#my-modal .my_modal_box").addEventListener('click', event => {
    event._isClickWithInModal = true;
});
document.getElementById("my-modal").addEventListener('click', event => {
    if (event._isClickWithInModal) return;
    event.currentTarget.classList.remove('open');
});

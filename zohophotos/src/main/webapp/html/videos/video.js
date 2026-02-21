/**
 * 
 */
const uploadInput = document.getElementById("videoUpload");
const sidebarVideos = document.getElementById("sidebarVideos");

let uploadedVideos = [];

// upload event
uploadInput.addEventListener("change", e => {
    const file = e.target.files[0];
    if (!file) return;

    const url = URL.createObjectURL(file);

    const videoObj = {
        name: file.name,
        url: url
    };

    uploadedVideos.push(videoObj);

    addVideoToSidebar(videoObj);
});


// add video preview to sidebar
function addVideoToSidebar(video) {

    const wrapper = document.createElement("div");
    wrapper.className = "sidebar-video";

    wrapper.innerHTML = `
        <video src="${video.url}" muted></video>
        <span>${video.name}</span>
    `;

    sidebarVideos.appendChild(wrapper);
}
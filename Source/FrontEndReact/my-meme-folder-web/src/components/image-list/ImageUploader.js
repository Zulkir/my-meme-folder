import React, {useCallback} from 'react'
import {useDropzone} from 'react-dropzone'
import axios from 'axios'

export default function ImageUploader(props) {
    const onDrop = useCallback(acceptedFiles => {
        if (acceptedFiles.length !== 1) {
            console.error("Exactly one file expected");
            return;
        }
        const formData = new FormData();
        const file = acceptedFiles[0];
        formData.append("file", file);
        axios.post(`/api/images/?path=${encodeURI(props.getPath())}&name=${file.name}`, formData, {
            headers:{
                "Content-Type": "multipart/form-data"
            }
        }).then(res => {
            if (res.status === 200)
                props.onUploaded();
            else
                console.log(res);
        }).catch(e => {
            console.log(e);
        });
    }, []);

    const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop});

    return (
        <div {...getRootProps()} className="image-list-item">
            <input {...getInputProps()} />
            <table><tbody><tr><td>{
                isDragActive ?
                    <p>Drop the image here ...</p> :
                    <p>Add new image</p>
            }</td></tr></tbody></table>
        </div>
    )
}
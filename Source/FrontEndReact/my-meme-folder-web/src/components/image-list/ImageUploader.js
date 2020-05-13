import React, {useCallback} from 'react'
import {useDropzone} from 'react-dropzone'

export default function ImageUploader() {
    const onDrop = useCallback(acceptedFiles => {
        // Do something with the files
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
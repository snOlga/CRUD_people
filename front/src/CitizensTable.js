import React from 'react';
import { useState } from 'react';

function CitizensTable({ jsonData }) {
    const displayData = jsonData.map(
        (citizen) => {
            let gender = citizen.gender =='1' ? 'male' : 'female';
            return (
                <tr>
                    <td>{citizen.id}</td>
                    <td>{citizen.name}</td>
                    <td>{gender}</td>
                    <td style={{ backgroundColor: citizen.eyeColor }}></td>
                    <td style={{ backgroundColor: citizen.hairColor }}></td>
                    <td>{citizen.height}</td>
                    <td>{citizen.nationality}</td>
                    <td>{citizen.passportID}</td>
                    <td>{new Date(citizen.birthday).toDateString()}</td>
                    <td>{citizen.owner}</td>
                </tr>
            )
        }
    )

    return (
        <table>
            <thead>
                <tr>
                    <th>id</th>
                    <th>name</th>
                    <th>gender</th>
                    <th>eye color</th>
                    <th>hair color</th>
                    <th>height</th>
                    <th>nationality</th>
                    <th>passport ID</th>
                    <th>birthday</th>
                    <td>owner</td>
                </tr>
            </thead>
            <tbody>
                {displayData}
            </tbody>
        </table>
    );
}

export default CitizensTable;
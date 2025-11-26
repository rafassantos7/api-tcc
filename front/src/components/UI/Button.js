import React from 'react';

export function Button({ children, ...props }) {
  return (
    <button {...props} className="bg-purple-600 text-white px-4 py-2 rounded">
      {children}
    </button>
  );
}

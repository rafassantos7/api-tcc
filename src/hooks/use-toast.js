import { useState } from 'react';

export function useToast() {
  const [message, setMessage] = useState(null);

  function toast(msg) {
    setMessage(msg);
    setTimeout(() => setMessage(null), 3000);
  }

  return { toast, message };
}
